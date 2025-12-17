package com.delecrode.devhub.data.remote.webApi.instance.intercptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

class FullLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // ===== REQUEST =====
        Log.d("Interceptor Method + URL", "${request.method}: ${request.url.toString()}")
        Log.d("Interceptor Header", request.headers.toString())

        request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            Log.d("Interceptor Buffer", buffer.readUtf8())
        }

        val startTime = System.nanoTime()
        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        // ===== RESPONSE =====
        Log.d("Interceptor Code", response.code.toString())
        Log.d("Interceptor Message", response.message)
        Log.d("Interceptor Header", response.headers.toString())

        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)

        val buffer = source?.buffer
        val responseString = buffer?.clone()?.readUtf8()

        Log.d("Interceptor Response", responseString ?: "")

        Log.d(
            "Interceptor Time",
            "Took ${(endTime - startTime) / 1e6} ms"
        )

        return response
    }
}
