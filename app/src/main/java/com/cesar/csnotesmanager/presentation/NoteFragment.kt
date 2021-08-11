package com.cesar.csnotesmanager.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cesar.core.data.Note
import com.cesar.csnotesmanager.R
import com.cesar.csnotesmanager.databinding.FragmentNoteBinding
import com.cesar.csnotesmanager.framework.viewmodel.NoteViewModel

class NoteFragment : Fragment() {

    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private lateinit var imm: InputMethodManager
    private var currentNote = Note("", "", 0L, 0L)
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if(noteId != 0L){
            viewModel.getNote(noteId)
        }

        imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.checkButton.setOnClickListener {
            if(binding.titleView.text.toString() != "" || binding.contentView.text.toString() != ""){
                val time = System.currentTimeMillis()
                currentNote.title = binding.titleView.text.toString()
                currentNote.content = binding.contentView.text.toString()
                currentNote.updateTime = time
                if(currentNote.id == 0L){
                    currentNote.creationTime = time
                }

                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(binding.titleView).popBackStack()
            } else {
                Toast.makeText(context, "Something went wron, please try again", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                currentNote = it
                binding.titleView.setText(it.title, TextView.BufferType.EDITABLE)
                binding.contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }

    private fun hideKeyboard(){
        imm.hideSoftInputFromWindow(binding.titleView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteNote -> {
                if(context != null && noteId != 0L){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes") { _, _ ->
                             viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("Cancel") { _, _ ->}
                        .create()
                        .show()
                }
            }
        }

        return true
    }

}