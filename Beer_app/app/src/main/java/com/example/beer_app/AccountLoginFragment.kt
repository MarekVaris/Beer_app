package com.example.beer_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class AccountLoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_account_login, container, false)

        val loginNavigatorButton = rootView.findViewById<Button>(R.id.login_account_button)
        val signUpNavigatorButton = rootView.findViewById<Button>(R.id.signup_account_button)

        loginNavigatorButton.setOnClickListener {
            rootView.findNavController().navigate(R.id.action_accountLoginFragment_to_loginFragment)
        }

        signUpNavigatorButton.setOnClickListener {
            rootView.findNavController().navigate(R.id.action_accountLoginFragment_to_signUpFragment)
        }

        return rootView
    }
}
