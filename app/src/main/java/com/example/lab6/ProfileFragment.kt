package com.example.lab6

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val args: ProfileFragmentArgs by navArgs()
    //private val viewModel by viewModel<ProfileViewModel>()
    private val viewModel:ProfileViewModel by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewFields = mapOf<String, TextView>(
            "name" to et_name,
            "login" to et_login,
            "password" to et_password
        )
        viewModel.user.observe(viewLifecycleOwner) { foundUser ->
            if (foundUser == null) {
                Utils.showToast(
                    requireContext(),
                    "Ошибка. Пользователь не найден.", Toast.LENGTH_SHORT
                )
            } else {
                for ((k, v) in viewFields) {
                    when (k) {
                        "name" -> v.text = foundUser.name
                        "login" -> v.text = foundUser.login
                        "password" -> v.text = foundUser.password
                    }
                }
            }
        }


        btn_exit.setOnClickListener {
            goToSignInFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser(args.userId)
    }

    private fun goToSignInFragment() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
        )
    }

}