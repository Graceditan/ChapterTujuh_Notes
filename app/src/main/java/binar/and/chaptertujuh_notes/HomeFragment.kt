package binar.and.chaptertujuh_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import binar.and.chaptertujuh_notes.data.NoteAdapter
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class NoteFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNote()
        add.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addFragment)
        }
        home_profile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    @DelicateCoroutinesApi
    fun getNote(){
        GlobalScope.launch {
            val result = noteDatabase?.notedao()?.getNote()
            requireActivity().runOnUiThread {
                if (result != null) {
                    rv_note.layoutManager = LinearLayoutManager(requireContext())
                    rv_note.adapter = NoteAdapter(result) {
                        val bundle = bundleOf("detail" to it)
                        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                    }
                }
            }
        }
    }
}