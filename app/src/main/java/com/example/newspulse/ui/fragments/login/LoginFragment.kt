package com.example.newspulse.ui.fragments.login

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.newspulse.utils.goneIf
import com.example.newspulse.utils.visibleIf

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
        setupObservers()
        setupPasswordVisibilityToggle()
        toggleRegistrationViews(false)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.email.requestFocus()
        }, 1000)
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun setupListeners() {
        binding.apply {
            login.setOnClickListener {
                toggleFormVisibility(false)
            }
            register.setOnClickListener {
                toggleFormVisibility(true)
            }

            logRegButton.setOnClickListener {
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

    private fun toggleFormVisibility(b: Boolean) {
        binding.apply {
            toggleRegistrationViews(b)
            clearEditTextFields()
            if (b) name.requestFocus() else email.requestFocus()
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            registrationResult.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
                } else {
                    Toast.makeText(
                        mContext, getString(R.string.register_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            loginResult.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
                } else {
                    Toast.makeText(
                        mContext, getString(R.string.login_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun toggleRegistrationViews(isVisible: Boolean): Boolean {
        binding.apply {
            name visibleIf isVisible
            lineLogin goneIf isVisible
            lineRegister visibleIf isVisible
            register.setTypeface(null, if (isVisible) Typeface.BOLD else Typeface.NORMAL)
            login.setTypeface(null, if (isVisible) Typeface.NORMAL else Typeface.BOLD)
            logRegButton.text =
                if (isVisible) getString(R.string.register) else getString(R.string.login)
        }
        return false
    }

    private fun togglePasswordVisibility() {
        binding.password.apply {
            val isVisible = transformationMethod != null
            val drawableId = if (isVisible) R.drawable.show else R.drawable.hide
            val drawable = ContextCompat.getDrawable(mContext, drawableId)
            transformationMethod =
                if (isVisible) null else PasswordTransformationMethod.getInstance()
            setSelection(text?.length ?: 0)
            drawable?.let { setCompoundDrawablesWithIntrinsicBounds(null, null, it, null) }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding.password.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun clearEditTextFields() {
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
