package com.example.organizer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class taskDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME ="organizer.db"
        private const val DATABASE_VERSION =1
        private const val TABLE_NAME ="alltasks"
        private const val COLUMN_ID ="id"
        private const val COLUMN_TITLE ="title"
        private const val COLUMN_DESC  ="description"
        private const val COLUMN_DEADLINE  ="deadline"
        private const val COLUMN_PRIORITY  ="priority"




    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT , $COLUMN_DESC TEXT, $COLUMN_DEADLINE TEXT, $COLUMN_PRIORITY INTEGER)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTAbleQuery =" DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTAbleQuery)
        onCreate(db)
    }

    fun insertTask(task: Task){
        val db = writableDatabase
        val values= ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESC, task.description)
            put(COLUMN_DEADLINE, task.deadline)
            put(COLUMN_PRIORITY, task.priority)

        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

}