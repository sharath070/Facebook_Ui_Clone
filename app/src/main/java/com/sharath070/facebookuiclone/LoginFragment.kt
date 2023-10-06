package com.sharath070.facebookuiclone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.sharath070.facebookuiclone.Constants.Companion.PASSWORD
import com.sharath070.facebookuiclone.Constants.Companion.USERNAME
import com.sharath070.facebookuiclone.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {

            val userName = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            if (userName.isEmpty()) {
                binding.edtUsername.requestFocus()
                showSoftKeyboard(binding.edtUsername)
            } else if (password.isEmpty()) {
                binding.edtPassword.requestFocus()
                showSoftKeyboard(binding.edtPassword)
            } else if (validateUserNameAndPassword(userName, password)) {

                sharedPreferences.edit().putBoolean("login", true).apply()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

            } else {
                Toast.makeText(requireContext(), "Incorrect Login Credentials", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun validateUserNameAndPassword(userName: String, password: String): Boolean {
        return userName == USERNAME && password == PASSWORD
    }

    private fun showSoftKeyboard(editText: EditText) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}