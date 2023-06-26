package com.example.daftarmahasiswa.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daftarmahasiswa.R
import com.example.daftarmahasiswa.application.StudentApp
import com.example.daftarmahasiswa.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((applicationContext as StudentApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StudentListAdapter { student ->
            // ini list yang bisa di klik dan mendapatkan data student jadi tidak null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(student)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        studentViewModel.allstudents.observe(viewLifecycleOwner){students ->
            students.let {
                if(students.isEmpty()){
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationImageView.visibility = View.VISIBLE
                } else{
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationImageView.visibility = View.GONE
                }

                adapter.submitList(students)
            }

        }


      binding.addFAB.setOnClickListener {
        // ini button tambah jadi  student pasti null
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
      findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}