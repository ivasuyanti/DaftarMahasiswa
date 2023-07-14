package com.example.daftarmahasiswa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.daftarmahasiswa.R


class StartFragment : Fragment() {
    private lateinit var pinEditText: EditText
    private lateinit var startButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        pinEditText = view.findViewById(R.id.pinEditText)
        startButton = view.findViewById(R.id.startButton)

        startButton.setOnClickListener {
            val username = pinEditText.text.toString()


            // Perform login authentication
            val isAuthenticated = authenticateUser(username)

            if (isAuthenticated) {
                // Login success, perform necessary actions
                Toast.makeText(activity, "Get started Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_content_main
                )
                navController.navigate(R.id.action_StartFragment_to_PointFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun authenticateUser(pin: String): Boolean {
        // Implement your authentication logic here
        // This is just a sample implementation
        return pin == "071001"
    }
}
