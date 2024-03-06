package com.example.newspulse.ui.fragments.login

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newspulse.R
import com.example.newspulse.databinding.FragmentLoginBinding
import com.example.newspulse.utils.hide
import com.example.newspulse.utils.show

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setupListeners()
        binding.email.requestFocus()
        setupObservers()
        setupPasswordVisibilityToggle()
        toggleRegistrationVisibility(false)
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun setupListeners() {
        binding.apply {
            login.setOnClickListener {
                toggleRegistrationVisibility(false)
                clearEditText()
                email.requestFocus()
            }
            register.setOnClickListener {
                toggleRegistrationVisibility(true)
                clearEditText()
                name.requestFocus()
            }

            binding.logRegButton.setOnClickListener {
                val name = name.text.toString()
                val email = email.text.toString()
                val password = password.text.toString()
                if (binding.logRegButton.text == getString(R.string.register)) {
                    viewModel.registerUser(name, email, password)
                } else {
                    viewModel.loginUser(email, password)
                }
            }

        }
    }

    private fun setupObservers() {
        viewModel.registrationResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
            } else {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
            } else {
                Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleRegistrationVisibility(isVisible: Boolean): Boolean {
        binding.apply {
            if (isVisible) {
                name.show()
                lineLogin.hide()
                lineRegister.show()
                register.setTypeface(null, Typeface.BOLD)
                login.setTypeface(null, Typeface.NORMAL)
                logRegButton.text = getString(R.string.register)
            } else {
                name.hide()
                lineLogin.show()
                lineRegister.hide()
                login.setTypeface(null, Typeface.BOLD)
                register.setTypeface(null, Typeface.NORMAL)
                logRegButton.text = getString(R.string.login)
            }
        }
        return false
    }

    private fun togglePasswordVisibility() {
        val isVisible = binding.password.transformationMethod != null
        val drawableId = if (isVisible) R.drawable.show else R.drawable.hide
        val drawable = ContextCompat.getDrawable(mContext, drawableId)
        binding.password.transformationMethod = if (isVisible) null else PasswordTransformationMethod.getInstance()
        drawable?.let { binding.password.setCompoundDrawablesWithIntrinsicBounds(null, null, it, null) }
    }

    private fun setupPasswordVisibilityToggle() {
        binding.password.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun clearEditText(){
        binding.apply {
            name.text?.clear()
            email.text?.clear()
            password.text?.clear()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
