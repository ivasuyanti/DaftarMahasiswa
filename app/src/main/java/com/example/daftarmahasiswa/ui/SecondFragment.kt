package com.example.daftarmahasiswa.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.daftarmahasiswa.R
import com.example.daftarmahasiswa.application.StudentApp
import com.example.daftarmahasiswa.databinding.FragmentSecondBinding
import com.example.daftarmahasiswa.model.Student

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((applicationContext as StudentApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var student: Student? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        student = args.student
        // kita cek jika student null maka tampilan default nambah daftar mahasiswa
        // jika student tidak null tampilan sedikit berubah ada tombol hapus dan ubah
        if(student != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(student?.name)
            binding.majorEditText.setText(student?.major)
            binding.clazzEditText.setText(student?.clazz)
            binding.addressEditText.setText(student?.address)
        }

        val name = binding.nameEditText.text
        val major = binding.majorEditText.text
        val clazz = binding.clazzEditText.text
        val address = binding.addressEditText.text

        binding.saveButton.setOnClickListener {
            // kita kasih kondisi jika kosong tidak bisa nyimpan
            if(name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(major.isEmpty()) {
            Toast.makeText(context,"jurusan tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else if(clazz.isEmpty()) {
            Toast.makeText(context,"Kelas tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if(address.isEmpty()) {
            Toast.makeText(context,"Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else {

                if(student == null){
                    val student = Student(student?.id!!, name.toString(), major.toString(), clazz.toString(), address.toString())
                    studentViewModel.insert(student)
                } else {
                    val student = Student(0, name.toString(), major.toString(), clazz.toString(), address.toString())
                    studentViewModel.update(student)
                }

                findNavController().popBackStack() //untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            student?.let { studentViewModel.delete(it) }
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}