package com.example.lab6

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    //private val viewModel by viewModel<ProfileViewModel>()
    private val viewModel: ProfileViewModel by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_recover_password.setOnClickListener {
            goToRecoverFragment()
        }
        btn_login_sign_up.setOnClickListener {
            goToSignUpFragment()
        }
        btn_login_sign_in.setOnClickListener {
            if (et_sign_in_login.text.toString().isNotEmpty()
                && et_sign_in_password.text.toString().isNotEmpty()
            ) {
                val login = et_sign_in_login.text.toString()
                val password = et_sign_in_password.text.toString()

                viewModel.loginRequest(
                    login,
                    password
                ) { user: User? ->
                    if (user != null) {

                        goToProfileFragment(user.id)
                    } else {
                        Utils.showToast(
                            requireContext(),
                            "Пользователь не найден", Toast.LENGTH_SHORT
                        )
                    }
                }

            } else {
                Utils.showToast(
                    requireContext(),
                    "Заполнены не все поля", Toast.LENGTH_SHORT
                )
            }
        }
    }

    private fun goToSignUpFragment() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        )
    }

    private fun goToProfileFragment(userId: Long) {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToProfileFragment(userId)
        )
    }

    private fun goToRecoverFragment() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToRecoverFragment()
        )
    }
}