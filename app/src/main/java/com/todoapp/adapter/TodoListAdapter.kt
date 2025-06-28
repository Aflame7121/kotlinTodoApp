package com.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.data.model.Todo
import com.todoapp.databinding.ItemTodoBinding

/**
 * Efficient RecyclerView adapter for rendering Todo items with improved scrolling performance.
 * Uses ListAdapter with DiffUtil for optimized updates and view recycling.
 */
class TodoListAdapter(
    private val onItemClick: (Todo) -> Unit,
    private val onCompleteToggle: (Todo) -> Unit
) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return TodoViewHolder(binding, onItemClick, onCompleteToggle)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onItemClick: (Todo) -> Unit,
        private val onCompleteToggle: (Todo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.apply {
                // Set text with null safety
                tvTodoTitle.text = todo.title ?: ""
                cbTodoComplete.isChecked = todo.isCompleted

                // Handle item click
                root.setOnClickListener { onItemClick(todo) }

                // Handle complete toggle
                cbTodoComplete.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked != todo.isCompleted) {
                        onCompleteToggle(todo.copy(isCompleted = isChecked))
                    }
                }
            }
        }
    }

    /**
     * DiffUtil callback to efficiently update RecyclerView items
     * Helps minimize unnecessary view updates and improve performance
     */
    private class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}