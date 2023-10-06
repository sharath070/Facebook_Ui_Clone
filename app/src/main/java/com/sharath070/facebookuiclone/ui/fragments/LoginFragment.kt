package com.sharath070.facebookuiclone.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sharath070.facebookuiclone.R
import com.sharath070.facebookuiclone.databinding.FragmentLoginBinding
import com.sharath070.facebookuiclone.model.User
import com.sharath070.facebookuiclone.ui.activities.MainActivity
import com.sharath070.facebookuiclone.viewModels.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)

        viewModel.getUsers().observe(viewLifecycleOwner){ userList ->

            binding.btnLogin.setOnClickListener {

                val userName = binding.edtUsername.text.toString().trim()
                val password = binding.edtPassword.text.toString()
                val userToCheck = User(userName, password)

                val isUserInList = userList.contains(userToCheck)

                Log.d("@@@@", userList.toString())
                Log.d("@@@@", isUserInList.toString())

                if (userName.isEmpty()) {
                    binding.edtUsername.requestFocus()
                    showSoftKeyboard(binding.edtUsername)
                } else if (password.isEmpty()) {
                    binding.edtPassword.requestFocus()
                    showSoftKeyboard(binding.edtPassword)
                } else if (isUserInList) {

                    sharedPreferences.edit().putBoolean("login", true).apply()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else {
                    Toast.makeText(requireContext(), "Incorrect Login Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }


        binding.btnCreateAcc.setOnClickListener {
            openDialog()
        }

    }

    private fun openDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialog = inflater.inflate(R.layout.create_acc_dialog, null)

        val userName = dialog.findViewById<EditText>(R.id.createUsername)
        val password = dialog.findViewById<EditText>(R.id.createPassword)

        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnCreate = dialog.findViewById<Button>(R.id.btnCreate)

        val tvRequirements = dialog.findViewById<TextView>(R.id.tvRequirements)

        val alertDialog = builder.setView(dialog).create() // Create the AlertDialog instance

        btnCancel.setOnClickListener {
            // Dismiss the AlertDialog when "Cancel" button is clicked
            alertDialog.dismiss()
        }

        btnCreate.setOnClickListener {
            val userNameString = userName.text.toString()
            val passwordString = password.text.toString()

            if (userName.text.isEmpty()) {
                userName.requestFocus()
                showSoftKeyboard(userName)
            } else if (password.text.isEmpty()) {
                password.requestFocus()
                showSoftKeyboard(password)
            } else if (validateUserNameAndPassword(userNameString, passwordString)) {
                viewModel.saveUser(User(userNameString, passwordString))
                tvRequirements.visibility = View.GONE

                sharedPreferences.edit().putBoolean("login", true).apply()

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                alertDialog.dismiss() // Dismiss the AlertDialog after successful action
            } else {
                tvRequirements.visibility = View.VISIBLE
            }
        }

        alertDialog.show()
    }


    private fun validateUserNameAndPassword(userName: String, password: String): Boolean {
        val pattern =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^*\\(\\)-_+={}\\[\\]|\\\\;:<>,./?]).+\$".toRegex()
        return pattern.matches(password)
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