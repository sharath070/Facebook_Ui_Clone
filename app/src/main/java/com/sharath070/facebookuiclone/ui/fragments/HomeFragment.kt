package com.sharath070.facebookuiclone.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sharath070.facebookuiclone.R
import com.sharath070.facebookuiclone.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)

        binding.button.setOnClickListener {

            sharedPreferences.edit().putBoolean("login", false).apply()
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}