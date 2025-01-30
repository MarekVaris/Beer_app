package com.example.beer_app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val  rootView = inflater.inflate(R.layout.fragment_login, container, false)
        val goToSignUpButton = rootView.findViewById<TextView>(R.id.go_to_sign_up_button)

        goToSignUpButton.setOnClickListener(){
            rootView.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return rootView
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

}

private fun LoginFragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

