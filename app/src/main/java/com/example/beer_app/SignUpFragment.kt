package com.example.beer_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson


class SignUpFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val signUpButton = rootView.findViewById<Button>(R.id.signup_button)
        val usernameInput = rootView.findViewById<TextInputEditText>(R.id.username_input)
        val passwordInput = rootView.findViewById<TextInputEditText>(R.id.password_input)
        var goToLoginButton = rootView.findViewById<TextView>(R.id.go_to_login_button)

        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val newUser = User(username, password)

                if (UserStorage.addUser(requireContext(), newUser)) {
                    Toast.makeText(requireContext(), "User Registered Successfully!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireContext(), "Username already exists!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }

        goToLoginButton?.setOnClickListener {
            rootView.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        return rootView
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootLayout = view.findViewById<ConstraintLayout>(R.id.signup_layout)
        rootLayout.setOnTouchListener { _, _ ->
            hideKeyboard()
            true
        }
    }

}

private fun SignUpFragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}