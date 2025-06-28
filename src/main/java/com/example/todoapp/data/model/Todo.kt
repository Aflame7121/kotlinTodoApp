package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // Validate todo item input
    init {
        require(title.isNotBlank()) { "Todo title cannot be blank" }
        require(title.length <= 100) { "Todo title cannot exceed 100 characters" }
        description?.let {
            require(it.length <= 500) { "Todo description cannot exceed 500 characters" }
        }
    }
}