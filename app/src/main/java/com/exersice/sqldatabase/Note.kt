package com.exersice.sqldatabase

/**
 * A data class representing a single Note.
 *
 * This is the standard Kotlin way to create a model class. The `data`
 * keyword automatically generates getters, setters, toString(), equals(), etc.
 * Default values are provided to allow for easy object creation.
 */
data class Note(
    var id: Int = 0,
    var title: String = "",
    var content: String = ""
)