package binar.and.chaptertujuh_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import binar.and.chaptertujuh_notes.data.NoteDatabase
import binar.and.chaptertujuh_notes.data.NoteManager
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteManager.ceklogin.asLiveData().observe(viewLifecycleOwner){
            if (it != false){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
        login_button.setOnClickListener {
            val username = login_email.text.toString()
            val password = login_password.text.toString()
            loginuser(username,password)
        }
        login_daftar.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    fun loginuser(username : String, password :String) {
        GlobalScope.async {
            val user = noteDatabase?.notedao()?.getUserRegistered(username)
            requireActivity().runOnUiThread{
                if (user != null){
                    GlobalScope.launch {
                        noteManager.setBoolean(true)
                        noteManager.saveData(username)
                    }
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }else{
                    Toast.makeText(requireContext(), "Password yang anda masukkan salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}