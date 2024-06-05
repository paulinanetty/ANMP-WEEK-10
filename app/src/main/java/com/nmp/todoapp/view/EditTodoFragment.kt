package com.nmp.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nmp.todoapp.R
import com.nmp.todoapp.databinding.FragmentCreateTodoBinding
import com.nmp.todoapp.databinding.FragmentEditTodoBinding
import com.nmp.todoapp.model.Todo
import com.nmp.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment(),RadioClickListener,TodoEditClick {

    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var viewModel: DetailTodoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.txttitleheading.text = "Edit Todo"
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
//        binding.btnSubmit.setOnClickListener {
//            val radio =
//                view.findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
//            viewModel.update(
//                binding.txttitle.text.toString(),
//                binding.txtnotes.text.toString(), radio.tag.toString().toInt(), uuid
//            )
//            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }
        binding.radioListener = this
        binding.saveListener = this
        observeViewModel()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater,container,
            false)
        return binding.root
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
//            binding.txttitle.setText(it.title)
//            binding.txtnotes.setText(it.notes)
//
//            when (it.priority) {
//                1 -> binding.radioButtonlow.isChecked = true
//                2 -> binding.radioButtonmed.isChecked = true
//                else -> binding.radioButtonhigh.isChecked = true
//            }
            binding.todo = it
        })
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onTodoEditClick(v: View) {
        TODO("Not yet implemented")
    }

    override fun onTodoSaveChangesClick(v: View,obj: Todo) {
        viewModel.update(obj.title, obj.notes, obj.priority, obj.uuid)
        Toast.makeText(v.context, "Todo Updated", Toast.LENGTH_SHORT).show()

    }

}