package binar.and.chaptertujuh_notes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import binar.and.chaptertujuh_notes.data.Note
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save_addfragmen.setOnClickListener {
            val judul = et_title_addact.text.toString()
            val isi = et_notes_addactivity.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            tambahnote(Note(null,judul,formatted.toString(),isi))
        }
    }
    fun tambahnote(note: Note){
        GlobalScope.async {
            val result = noteDatabase?.notedao()?.addNote(note)
            requireActivity().runOnUiThread(){
                if(result != 0.toLong()){
                    Toast.makeText(requireContext(),"note berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(requireView()).navigate(R.id.action_addFragment_to_homeFragment)
                }
            }
        }
    }
}