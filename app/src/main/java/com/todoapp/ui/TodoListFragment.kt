package com.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.adapter.TodoListAdapter
import com.todoapp.databinding.FragmentTodoListBinding
import com.todoapp.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment for displaying and managing the list of Todo items
 * Implements efficient scrolling and list rendering
 */
@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoAdapter: TodoListAdapter
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeTodos()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoListAdapter(
            onItemClick = { /* Handle item click */ },
            onCompleteToggle = { todo ->
                viewModel.updateTodo(todo)
            }
        )

        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
            
            // Optimize scrolling performance
            setHasFixedSize(true)
            
            // Add scroll listener for potential lazy loading or performance tracking
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    // Optional: Add scroll state handling if needed
                }
            })
        }
    }

    private fun observeTodos() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todos.collect { todos ->
                // Use submitList for efficient updates
                todoAdapter.submitList(todos)
                
                // Show/hide empty state
                binding.tvEmptyState.visibility = if (todos.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}