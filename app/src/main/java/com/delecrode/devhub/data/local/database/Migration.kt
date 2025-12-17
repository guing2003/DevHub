package com.delecrode.devhub.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE repositories_new (
                idLocal INTEGER NOT NULL PRIMARY KEY,
                id INTEGER NOT NULL,
                name TEXT NOT NULL,
                userName TEXT NOT NULL,
                description TEXT,
                url TEXT NOT NULL
            )
        """)

        db.execSQL("""
            INSERT INTO repositories_new (
                idLocal, id, name, userName, description, url
            )
            SELECT
                idLocal, id, name, userName, description, url
            FROM repositories
        """)

        db.execSQL("DROP TABLE repositories")
        db.execSQL("ALTER TABLE repositories_new RENAME TO repositories")
    }
}

