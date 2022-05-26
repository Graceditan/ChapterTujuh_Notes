package binar.and.chaptertujuh_notes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import binar.and.chaptertujuh_notes.data.Note
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Suppress("DeferredResultUnused")
@DelicateCoroutinesApi
class EditFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteDetail = arguments?.getParcelable<Note>("detailEdit") as Note
        et_title_editact.setText(noteDetail.judul)
        et_notes_editactivity.setText(noteDetail.isi)

        btn_save_editdialog.setOnClickListener {
            val judul = et_title_editact.text.toString()
            val isi = et_notes_editactivity.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val up = Note(noteDetail.id,judul,formatted.toString(),isi)
            GlobalScope.async {
                val result = noteDatabase?.notedao()?.updateNote(up)

                requireActivity().runOnUiThread{
                    if (result != 0){
                        Navigation.findNavController(view).navigate(R.id.action_editFragment_to_homeFragment)
                    }
                }
            }
        }
    }
}