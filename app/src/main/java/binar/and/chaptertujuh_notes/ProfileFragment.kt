package binar.and.chaptertujuh_notes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@Suppress("DeferredResultUnused")
class ProfileFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteManager.userName.asLiveData().observe(viewLifecycleOwner){
            GlobalScope.async {
                val user = noteDatabase?.notedao()?.getUserRegistered(it)
                requireActivity().runOnUiThread{

                }
            }
        }
        profilr_logout.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
            GlobalScope.launch {
                noteManager.setBoolean(false)
            }
        }
    }
}