package binar.and.chaptertujuh_notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import binar.and.chaptertujuh_notes.data.Note
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Suppress("DeferredResultUnused")
@DelicateCoroutinesApi
class DetailFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteDetail = arguments?.getParcelable<Note>("detail") as Note
        detail_judul.text =  noteDetail.judul
        detail_isi.text =  noteDetail.isi
        detail_waktu.text =  noteDetail.waktu

        detail_btnedit.setOnClickListener {
            val bundle = bundleOf("detailEdit" to noteDetail)
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_editFragment, bundle)
        }
        detail_btndelete.setOnClickListener {
            GlobalScope.async {
                val result = noteDatabase?.notedao()?.deleteNote(noteDetail)
                requireActivity().runOnUiThread{
                    if (result == 1){
                        Toast.makeText(requireContext(),"terhapus", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_homeFragment)
                    }else{
                        Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}