package com.example.todo

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class NotesTable {

    data class Notes (
        val id: Int?,
        val title: String,
        val body : String
    )

    companion object {
        val TABLE_NAME = "notes"

        val CMD_CREATE_TABLE = """
           CREATE TABLE $TABLE_NAME (
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           title TEXT,
           body TEXT
           );
        """.trimIndent()

        fun insertTask(db: SQLiteDatabase, note: Notes) {

            val taskRow = ContentValues()
            taskRow.put("title", note.title)
            taskRow.put("body", note.body)

            db.insert(TABLE_NAME, null, taskRow)

        }

        fun getAllTasks(db: SQLiteDatabase): ArrayList<Notes> {

            val notes = ArrayList<Notes>()

            val cursor = db.query(
                TABLE_NAME,
                arrayOf("id", "title", "body"),
                null, null, //where
                null, // group by
                null, //having
                null //order
            )

            cursor.moveToFirst()

            val idCol = cursor.getColumnIndex("id")
            val titleCol = cursor.getColumnIndex("title")
            val bodyCol = cursor.getColumnIndex("body")

            while (cursor.moveToNext()) {
                val note = Notes(
                    cursor.getInt(idCol),
                    cursor.getString(titleCol),
                    cursor.getString(bodyCol)
                )
                notes.add(note)
            }
            cursor.close()
            return notes
        }
    }
}