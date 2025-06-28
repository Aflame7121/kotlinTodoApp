package com.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.model.TodoItem
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * RecyclerView adapter for displaying a list of todo items.
 * Uses DiffUtil for efficient list updates.
 */
class TodoListAdapter(
    private val onItemClick: (TodoItem) -> Unit,
    private val onCompletionToggle: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoListAdapter.TodoItemViewHolder>(TodoItemDiffCallback()) {

    /**
     * ViewHolder for individual todo item views
     */
    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.todo_title)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.todo_due_date)
        private val completionCheckBox: CheckBox = itemView.findViewById(R.id.todo_completion_checkbox)

        /**
         * Bind todo item data to the view
         */
        fun bind(todoItem: TodoItem) {
            titleTextView.text = todoItem.title
            
            // Format due date if available
            todoItem.dueDate?.let { dueDate ->
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                dueDateTextView.text = dateFormat.format(dueDate)
                dueDateTextView.visibility = View.VISIBLE
            } ?: run {
                dueDateTextView.visibility = View.GONE
            }

            // Set checkbox state
            completionCheckBox.isChecked = todoItem.isCompleted

            // Set click listeners
            itemView.setOnClickListener { onItemClick(todoItem) }
            completionCheckBox.setOnClickListener { 
                onCompletionToggle(todoItem) 
            }
        }
    }

    /**
     * Create new view holder for todo items
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item_layout, parent, false)
        return TodoItemViewHolder(view)
    }

    /**
     * Bind data to view holder
     */
    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * DiffUtil callback for efficient list updates
     */
    class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }
    }
}