package com.sharath070.facebookuiclone.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.sharath070.facebookuiclone.Constants.Companion.PASSWORD
import com.sharath070.facebookuiclone.Constants.Companion.USERNAME
import com.sharath070.facebookuiclone.R
import com.sharath070.facebookuiclone.databinding.CreateAccDialogBinding
import com.sharath070.facebookuiclone.databinding.FragmentLoginBinding
import com.sharath070.facebookuiclone.model.User
import com.sharath070.facebookuiclone.ui.activities.MainActivity
import com.sharath070.facebookuiclone.viewModels.UserViewModel

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


            binding.btnCreateAcc.setOnClickListener {
                openDialog()
            }
        }

    }

    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.create_acc_dialog)
        dialog.setCancelable(false)

        val userName = dialog.findViewById<EditText>(R.id.createUsername)
        val password = dialog.findViewById<EditText>(R.id.createPassword)

        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnCreate = dialog.findViewById<Button>(R.id.btnCreate)

        val tvRequirements = dialog.findViewById<TextView>(R.id.tvRequirements)

        btnCancel.setOnClickListener {
            dialog.dismiss()
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
                viewModel.saveUser(User(0, userNameString, passwordString))
                tvRequirements.visibility = View.GONE
                dialog.dismiss()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                tvRequirements.visibility = View.VISIBLE
            }
        }

    }

    private fun validateUserNameAndPassword(userName: String, password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*()-_+={}|\\;:<>,./?])(?=.{8,})$")
        val match = regex.find(password)
        return match != null
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