package com.example.beer_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val  rootView = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = rootView.findViewById<Button>(R.id.login_button)
        val usernameInput = rootView.findViewById<TextInputEditText>(R.id.username_input)
        val passwordInput = rootView.findViewById<TextInputEditText>(R.id.password_input)
        val goToSignUpButton = rootView.findViewById<TextView>(R.id.go_to_sign_up_button)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (validation(username, password)) {
                saveLoggedInUser(username)
                Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                rootView.findNavController().navigate(R.id.action_loginFragment_to_userInfoFragment)
            }
            else {
                Toast.makeText(requireContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show()
            }
        }

        goToSignUpButton.setOnClickListener {
            rootView.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return rootView
    }


    fun validation(username: String, password: String): Boolean {
        val users = UserStorage.loadUsers(requireContext())
        return users.any { it.username == username && it.password == password }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootLayout = view.findViewById<ConstraintLayout>(R.id.login_layout)
        rootLayout.setOnTouchListener { _, _ ->
            hideKeyboard()
            true
        }
    }

    private fun saveLoggedInUser(username: String) {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("logged_in_user", username)
            apply()
        }
    }

}

private fun LoginFragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

