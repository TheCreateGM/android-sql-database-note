package com.exersice.sqldatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notes_db"
        private const val TABLE_NOTES = "notes"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NOTES(" +
                "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$KEY_TITLE TEXT," +
                "$KEY_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    // --- "C"reate: Add a new note ---
    fun addNote(note: Note) {
        // .use will automatically close the database connection
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(KEY_TITLE, note.title)
                put(KEY_CONTENT, note.content)
            }
            db.insert(TABLE_NOTES, null, values)
        }
    }

    // --- "R"ead: Get a single note by ID ---
    fun getNote(id: Int): Note? {
        // .use will automatically close the database and cursor
        readableDatabase.use { db ->
            db.query(
                TABLE_NOTES,
                arrayOf(KEY_ID, KEY_TITLE, KEY_CONTENT),
                "$KEY_ID=?",
                arrayOf(id.toString()),
                null, null, null, null
            ).use { cursor -> // .use ensures the cursor is closed
                if (cursor.moveToFirst()) {
                    return Note(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                        content = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT))
                    )
                }
            }
        }
        return null // Return null if note is not found
    }

    // --- "R"ead: Get all notes ---
    fun getAllNotes(): List<Note> {
        val noteList = ArrayList<Note>()
        val selectQuery = "SELECT * FROM $TABLE_NOTES"

        // .use will automatically close the database and cursor
        readableDatabase.use { db ->
            db.rawQuery(selectQuery, null).use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val note = Note(
                            id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            content = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT))
                        )
                        noteList.add(note)
                    } while (cursor.moveToNext())
                }
            }
        }
        return noteList
    }

    // --- "U"pdate: Update an existing note ---
    fun updateNote(note: Note): Int {
        // .use will automatically close the database connection
        return writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(KEY_TITLE, note.title)
                put(KEY_CONTENT, note.content)
            }
            db.update(
                TABLE_NOTES,
                values,
                "$KEY_ID = ?",
                arrayOf(note.id.toString())
            )
        }
    }

    // --- "D"elete: Delete a note ---
    fun deleteNote(id: Int) {
        // .use will automatically close the database connection
        writableDatabase.use { db ->
            db.delete(
                TABLE_NOTES,
                "$KEY_ID = ?",
                arrayOf(id.toString())
            )
        }
    }
}