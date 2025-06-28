package com.todoapp.ui.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.model.TodoItem
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class TodoListAdapterTest {

    private lateinit var adapter: TodoListAdapter
    private lateinit var mockOnItemClick: (TodoItem) -> Unit
    private lateinit var mockOnCompletionToggle: (TodoItem) -> Unit

    @Before
    fun setup() {
        mockOnItemClick = {}
        mockOnCompletionToggle = {}
        adapter = TodoListAdapter(mockOnItemClick, mockOnCompletionToggle)
    }

    @Test
    fun `adapter submits list correctly`() {
        val todoItems = listOf(
            TodoItem(1, "Task 1", Date(), false),
            TodoItem(2, "Task 2", null, true)
        )
        adapter.submitList(todoItems)

        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun `view holder binds todo item correctly`() {
        val todoItem = TodoItem(1, "Test Task", Date(), false)
        val context = RuntimeEnvironment.getApplication()
        val parent = RecyclerView(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        
        // Use reflection to access private methods and fields
        val bindMethod = TodoListAdapter.TodoItemViewHolder::class.java
            .getDeclaredMethod("bind", TodoItem::class.java)
        bindMethod.isAccessible = true
        bindMethod.invoke(viewHolder, todoItem)

        // Use reflection to check private fields
        val titleTextView = viewHolder.itemView.findViewById<TextView>(R.id.todo_title)
        val dueDateTextView = viewHolder.itemView.findViewById<TextView>(R.id.todo_due_date)
        val completionCheckBox = viewHolder.itemView.findViewById<CheckBox>(R.id.todo_completion_checkbox)

        assertEquals("Test Task", titleTextView.text)
        assertTrue(dueDateTextView.visibility == View.VISIBLE)
        assertFalse(completionCheckBox.isChecked)
    }

    @Test
    fun `todo item with no due date handles visibility correctly`() {
        val todoItem = TodoItem(1, "No Date Task", null, false)
        val context = RuntimeEnvironment.getApplication()
        val parent = RecyclerView(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        
        val bindMethod = TodoListAdapter.TodoItemViewHolder::class.java
            .getDeclaredMethod("bind", TodoItem::class.java)
        bindMethod.isAccessible = true
        bindMethod.invoke(viewHolder, todoItem)

        val dueDateTextView = viewHolder.itemView.findViewById<TextView>(R.id.todo_due_date)
        assertTrue(dueDateTextView.visibility == View.GONE)
    }

    @Test
    fun `item click invokes callback`() {
        var clickedItem: TodoItem? = null
        val onItemClick: (TodoItem) -> Unit = { clickedItem = it }
        
        val todoItem = TodoItem(1, "Clickable Task")
        adapter = TodoListAdapter(onItemClick, mockOnCompletionToggle)
        
        val context = RuntimeEnvironment.getApplication()
        val parent = RecyclerView(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        
        val bindMethod = TodoListAdapter.TodoItemViewHolder::class.java
            .getDeclaredMethod("bind", TodoItem::class.java)
        bindMethod.isAccessible = true
        bindMethod.invoke(viewHolder, todoItem)

        // Simulate item click
        viewHolder.itemView.performClick()
        
        assertEquals(todoItem, clickedItem)
    }
}