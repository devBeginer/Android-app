package com.example.lab6

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_recover.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class RecoverFragment : Fragment(R.layout.fragment_recover) {
    //private val viewModel by viewModel<ProfileViewModel>()
    private val viewModel:ProfileViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var login = et_recover_login.text.toString()

        var randomCode = (100..999).random().toString()



        btn_recover_send.setOnClickListener {
            if (et_recover_login.text.toString().isNotEmpty()) {

                login = et_recover_login.text.toString()

                viewModel.findUser(login) { user: User? ->
                    if (user != null) {
                        til_recover_login.visibility = View.GONE
                        til_recover_code.visibility = View.VISIBLE

                        btn_recover_send.visibility = View.GONE
                        btn_recover_code.visibility = View.VISIBLE

                        showNotification(randomCode)
                    } else {
                        Utils.showToast(
                            requireContext(),
                            "Пользователь не существет", Toast.LENGTH_SHORT
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

        btn_recover_code.setOnClickListener {
            if (et_recover_code.text.toString().isNotEmpty()) {
                if (et_recover_code.text.toString() == randomCode) {
                    til_recover_code.visibility = View.GONE
                    btn_recover_code.visibility = View.GONE
                    til_recover_password.visibility = View.VISIBLE
                    btn_recover_password.visibility = View.VISIBLE
                    Utils.showToast(
                        requireContext(),
                        "Подтверждено. Введите новый пароль", Toast.LENGTH_SHORT
                    )
                } else {
                    Utils.showToast(
                        requireContext(),
                        "Код указан неверно",
                        Toast.LENGTH_SHORT
                    )
                    randomCode = (100..999).random().toString()
                    showNotification(randomCode)
                }
            } else {
                Utils.showToast(
                    requireContext(),
                    "Заполнены не все поля", Toast.LENGTH_SHORT
                )
            }
        }

        btn_recover_password.setOnClickListener {
            if (et_recover_password.text.toString().isNotEmpty()) {
                val password = et_recover_password.text.toString()

                if (password.matches(Regex(Utils.PASSWORD_PATTERN))) {
                    viewModel.recoverRequest(
                        login,
                        password
                    ) {
                        Utils.showToast(
                            requireContext(),
                            "Пароль восстновлен", Toast.LENGTH_SHORT
                        )

                        goToSignInFragment()

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

    fun showNotification(code: String) {
        /*val builder = AlertDialog.Builder(context)
        builder.setTitle("Код подтверждения")
        builder.setMessage(code)

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->

        }

        builder.show()*/

        val notificationManager =
            activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(requireContext(), "CODE")
            .setContentTitle("Код подтверждения")
            .setContentText(code)
            .setChannelId("CODE")
            .setSmallIcon(android.R.drawable.alert_light_frame)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "CODE",
                "name",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, notification)

    }

    private fun goToSignInFragment() {
        findNavController().navigate(
            RecoverFragmentDirections.actionRecoverFragmentToSignInFragment()
        )
    }
}