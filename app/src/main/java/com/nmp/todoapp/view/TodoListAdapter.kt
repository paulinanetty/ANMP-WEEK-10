package com.nmp.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nmp.todoapp.databinding.TodoItemlayoutBinding
import com.nmp.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener, TodoEditClick {
    class TodoViewHolder(var binding:TodoItemlayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        holder.binding.checkTask.text = todoList[position].title
//        holder.binding.checkTask.isChecked = false
//        holder.binding.checkTask.setOnCheckedChangeListener {
//                compoundButton, b ->
//            if(compoundButton.isPressed) {
//                adapterOnClick(todoList[position])
//            }
//        }
//
//        holder.binding.imageView2.setOnClickListener {
//            val action =
//                TodolistFragmentDirections.actioneditodo(todoList[position].uuid)
//
//            Navigation.findNavController(it).navigate(action)
//        }
        holder.binding.todo = todoList[position]
        holder.binding.listener = this
        holder.binding.checkTask.isChecked = false
        holder.binding.editListener = this

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemlayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }


    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(cb.isPressed){
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodolistFragmentDirections.actioneditodo(uuid)

        Navigation.findNavController(v).navigate(action)

    }

    override fun onTodoSaveChangesClick(v: View, obj: Todo) {
        TODO("Not yet implemented")
    }

}
