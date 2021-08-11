package com.cesar.csnotesmanager.presentation

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesar.csnotesmanager.databinding.FragmentListBinding
import com.cesar.csnotesmanager.framework.viewmodel.ListViewModel

class ListFragment : Fragment(), ListAction {

    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        binding.addNote.setOnClickListener { goToNoteDetails() }

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.notes.observe(viewLifecycleOwner, Observer { notesList ->
            binding.loadingView.visibility = View.GONE
            binding.notesListView.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notesList.sortedByDescending { it.updateTime })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L){
        val action = ListFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(binding.notesListView).navigate(action)
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }

}