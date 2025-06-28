package com.todoapp.model

import java.util.Date

/**
 * Represents a single todo item in the application.
 *
 * @property id Unique identifier for the todo item
 * @property title Title or description of the todo item
 * @property dueDate Date when the todo item is due
 * @property isCompleted Indicates whether the todo item is completed
 */
data class TodoItem(
    val id: Long = 0,
    val title: String,
    val dueDate: Date? = null,
    val isCompleted: Boolean = false
)