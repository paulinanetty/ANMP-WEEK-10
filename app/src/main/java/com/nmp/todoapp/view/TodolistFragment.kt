package com.nmp.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.todoapp.R
import com.nmp.todoapp.databinding.FragmentTodolistBinding
import com.nmp.todoapp.viewmodel.ListTodoViewModel


class TodolistFragment : Fragment() {

    private lateinit var binding: FragmentTodolistBinding
    private lateinit var viewModel: ListTodoViewModel
    private val todoListAdapter  = TodoListAdapter(arrayListOf(), {item -> viewModel.clearTask(item)})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        binding.recViewtodo.layoutManager = LinearLayoutManager(context)
        binding.recViewtodo.adapter = todoListAdapter

        binding.floatingActionButton.setOnClickListener {
            val action = TodolistFragmentDirections.actioncreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodolistBinding.inflate(inflater,container,false)
        return  binding.root
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            if(it.isEmpty()) {
                binding.recViewtodo?.visibility = View.GONE
                binding.txtError.setText("Your todo still empty.")
            } else {
                binding.recViewtodo?.visibility = View.VISIBLE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressBar?.visibility = View.GONE
            } else {
                binding.progressBar?.visibility = View.VISIBLE
            }
        })
        viewModel.todoLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError?.visibility = View.GONE
            } else {
                binding.txtError?.visibility = View.VISIBLE
            }
        })

    }


}