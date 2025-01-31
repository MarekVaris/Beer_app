package com.example.beer_app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController


class UserInfoFragment : Fragment() {
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("logged_in_user", "") ?: "Username"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_user_info, container, false)

        var usernameTextView = rootView.findViewById<TextView>(R.id.username_text_view)
        var accountSettingsButton = rootView.findViewById<Button>(R.id.account_settings_button)
        var logoutButton = rootView.findViewById<Button>(R.id.logout_button)

        usernameTextView.text = username

        logoutButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                remove("logged_in_user")
                apply()
            }
            Toast.makeText(requireContext(), "Logout Successful!", Toast.LENGTH_SHORT).show()
            rootView.findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment)
        }

        return rootView
    }


}