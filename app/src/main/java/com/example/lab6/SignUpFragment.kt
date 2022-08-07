package com.example.lab6

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    //private val viewModel by viewModel<ProfileViewModel>()
    private val viewModel: ProfileViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sign_up.setOnClickListener {
            if (et_sign_up_name.text.toString().isNotEmpty()
                && et_sign_up_login.text.toString().isNotEmpty()
                && et_sign_up_password.text.toString().isNotEmpty()
            ) {

                val name = et_sign_up_name.text.toString()
                val login = et_sign_up_login.text.toString()
                val password = et_sign_up_password.text.toString()

                if (password.matches(Regex(Utils.PASSWORD_PATTERN))) {
                    viewModel.registerRequest(
                        name = name,
                        login = login,
                        password = password
                    ) { user: User? ->
                        if (user != null) {
                            Utils.showToast(
                                requireContext(),
                                "Пользователь уже существет", Toast.LENGTH_SHORT
                            )
                        } else {
                            goToSignInFragment()
                        }
                    }
                } else {
                    Utils.showToast(
                        requireContext(),
                        "Пароль должен содержать не менее 8 символов, хотя бы 1 цифру и не должен содержать пробелов",
                        Toast.LENGTH_SHORT
                    )
                }
            } else {
                Utils.showToast(
                    requireContext(),
                    "Заполнены не все поля", Toast.LENGTH_SHORT
                )
            }
        }
    }

    private fun goToSignInFragment() {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        )
    }
}