package com.example.daftarmahasiswa.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daftarmahasiswa.R
import com.example.daftarmahasiswa.model.Student


class StudentListAdapter(
    private val onItemClickListener: (Student) -> Unit
): ListAdapter<Student, StudentListAdapter.StudentViewHolder>(WORDS_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student= getItem(position)
        holder.bind(student)
        holder.itemView.setOnClickListener {
            onItemClickListener(student)
        }
    }

    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView :TextView = itemView.findViewById(R.id.nameTextView)
        private val majorTextView :TextView = itemView.findViewById(R.id.majorTextView)
        private val clazzTextView :TextView = itemView.findViewById(R.id.clazzTextView)
        private val addressTextView :TextView = itemView.findViewById(R.id.addressTextView)

        fun bind(student: Student?) {
            nameTextView.text = student?.name
            majorTextView.text = student?.major
            clazzTextView.text = student?.clazz
            addressTextView.text = student?.address
        }

        companion object {
            fun create(parent: ViewGroup): StudentListAdapter.StudentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_student, parent, false)
                return StudentViewHolder(view)

            }
        }

    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Student>(){
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
   }

