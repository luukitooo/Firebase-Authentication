package com.example.myapplication.fragment

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.logInButton.setOnClickListener {
            if (!areLinesEmptyInsideLayout(binding.root, resources.getString(R.string.empty_line_error))) {
                logIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
                clearLinesInsideLayout(binding.root)
            }
        }
    }

    private fun areLinesEmptyInsideLayout(viewGroup: ViewGroup, errorMessage: String?): Boolean {
        viewGroup.children.forEach {
            if (it is EditText && it.text.isEmpty()) {
                it.error = errorMessage
                return true
            }
        }
        return false
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            when (it.isSuccessful) {
                true -> showToast(requireContext(), resources.getText(R.string.success).toString())
                else -> showToast(requireContext(), resources.getText(R.string.cant_log_in).toString())
            }
        }
    }

    private fun clearLinesInsideLayout(viewGroup: ViewGroup) {
        viewGroup.children.forEach { if (it is EditText) it.text.clear() }
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}