package com.todoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoapp.R
import com.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // Setup RecyclerView
        setupRecyclerView()

        // Observe todo items and update adapter
        todoViewModel.todoItems.observe(this) { todos ->
            todoAdapter.submitList(todos)
        }

        // Setup Add Todo button
        binding.addTodoButton.setOnClickListener {
            // TODO: Implement add todo functionality
            // This will be a placeholder for now
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onItemClick = { todo -> 
                // TODO: Implement edit todo functionality 
            },
            onDeleteClick = { todo ->
                // TODO: Implement delete todo functionality
            },
            onCompleteToggle = { todo ->
                // TODO: Implement toggle complete functionality
            }
        )

        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }
}