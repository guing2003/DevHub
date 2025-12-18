package com.delecrode.devhub.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun UserProfileHeader(avatar_url: String?,name: String, login: String, bio: String ) {

   Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center){
       Row(modifier = Modifier.fillMaxWidth()){
           if(avatar_url != null){
               Box(
                   modifier = Modifier
                       .background(Color.White, shape = CircleShape)
                       .wrapContentSize()
               ) {
                   AsyncImage(
                       model = avatar_url,
                       contentDescription = "Foto de Perfil",
                       modifier = Modifier
                           .size(68.dp)
                           .clip(CircleShape)
                   )
               }
           }
           Spacer(modifier = Modifier.width(6.dp))

           Column(
               modifier = Modifier.fillMaxWidth(),
               horizontalAlignment = Alignment.Start
           ) {


               Text(
                   text = name,
                   fontWeight = FontWeight.Bold,
                   fontSize = 28.sp,
                   color = MaterialTheme.colorScheme.onBackground
               )


               Text(
                   text = login,
                   fontWeight = FontWeight.Bold,
                   fontSize = 20.sp,
                   color = MaterialTheme.colorScheme.onBackground
               )


           }
       }

       Spacer(modifier = Modifier.height(12.dp))

       Text(
           text = bio,
           fontWeight = FontWeight.Normal,
           fontSize = 18.sp,
           textAlign = TextAlign.Start,
           color = MaterialTheme.colorScheme.onBackground
       )
   }
}