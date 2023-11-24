package com.achsanit.mealapp.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.achsanit.mealapp.R
import com.achsanit.mealapp.databinding.FragmentLoginBinding
import com.achsanit.mealapp.ui.MainActivity
import com.achsanit.mealapp.ui.modal.BottomSheetErrorFragment
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.disable
import com.achsanit.mealapp.utils.enable
import com.achsanit.mealapp.utils.makeGone
import com.achsanit.mealapp.utils.makeVisible
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton(false)

        val emailStream = RxTextView.textChanges(binding.edtEmail)
            .skipInitialValue()
            .map { email ->
                email.trim().isEmpty() || !isValidEmail(email)
            }
        emailStream.subscribe {
            showEmailError(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edtPassword)
            .skipInitialValue()
            .map {
                it.trim().isEmpty()
            }
        passwordStream.subscribe {
            showPasswordError(it)
        }

        val btnStream =
            Observable.combineLatest(emailStream, passwordStream) { emailInvalid, passwordInvalid ->
                !emailInvalid && !passwordInvalid
            }
        btnStream.subscribe {
            setButton(it)
        }

        observeLoginResponse()

        with(binding) {
            btnLogin.setOnClickListener {
                loginViewModel.login(edtEmail.text.toString(), edtPassword.text.toString())
            }
        }
    }

    private fun observeLoginResponse() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.pbLoading.makeVisible()
                }

                is Resource.Success -> {
                    binding.pbLoading.makeGone()
                    result.data?.let {
                        it.token?.let { token ->
                            loginViewModel.setLoginStatus(true, token)

                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }

                is Resource.Error -> {
                    binding.pbLoading.makeGone()
                    val message = when (result.codeError) {
                        400 -> "user not found"
                        else -> result.message.toString()
                    }

                    BottomSheetErrorFragment(message)
                        .show(parentFragmentManager, BottomSheetErrorFragment.MODAL_TAG)
                }

                else -> {
                    binding.pbLoading.makeGone()
                    BottomSheetErrorFragment(result.message.toString())
                        .show(parentFragmentManager, BottomSheetErrorFragment.MODAL_TAG)
                }
            }
        }
    }

    private fun showPasswordError(isNotValid: Boolean) {
        if (isNotValid) {
            binding.tilPassword.error = getString(R.string.text_error_empty_field)
        } else {
            binding.tilPassword.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun showEmailError(isNotValid: Boolean) {
        if (isNotValid) {
            binding.tilEmail.error = getString(R.string.text_error_empty_field)
        } else {
            binding.tilEmail.apply {
                error = null
                isErrorEnabled = false
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun setButton(isEnable: Boolean) {
        if (isEnable) {
            binding.btnLogin.apply {
                enable()
                setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue_foundation
                    )
                )
                setTextColor( ContextCompat.getColor(requireContext(),
                        android.R.color.white
                    )
                )
            }
        } else {
            binding.btnLogin.apply {
                disable()
                setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.darker_grey
                    )
                )
                setTextColor( ContextCompat.getColor( requireContext(), android.R.color.white )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}