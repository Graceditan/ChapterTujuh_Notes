package binar.and.chaptertujuh_notes.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and.chaptertujuh_notes.R
import kotlinx.android.synthetic.main.adapter_notes.view.*


class NoteAdapter(private val dataNote : List<Note>,
                  private val onClik :(Note)->Unit
): RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.adapter_notes, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataNote[position]

        holder.itemView.apply {
            notejudul.text = data.judul
            adapter_waktu.text = data.waktu
            rootView.setOnClickListener {
                onClik(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return  dataNote.size
    }
}