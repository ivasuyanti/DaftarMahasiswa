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


class PointFragment : Fragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_point, container, false)

        usernameEditText = view.findViewById(R.id.usernameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        registerButton = view.findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform register authentication
            val isAuthenticated = authenticateUser(username, email, password)

            if (isAuthenticated) {
                // Register success, perform necessary actions
                Toast.makeText(activity, "register Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_content_main
                )
                navController.navigate(R.id.action_PointFragment_to_LoginFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun authenticateUser(username: String, email: String, password: String): Boolean {
        // Implement your authentication logic here
        // This is just a sample implementation
        return username == "admin" && email == "ivasuyanti@gmail.com" && password == "12345"
    }
}
