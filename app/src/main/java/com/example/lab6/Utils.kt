package com.example.lab6

import android.content.Context
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.experimental.and

object Utils {
    const val PASSWORD_PATTERN = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$"

    fun showToast(context: Context, text: String, duration: Int) {
        val toast = Toast.makeText(context, text, duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view!!.background.setColorFilter(
            context.getColor(R.color.black),
            PorterDuff.Mode.SRC_IN
        )
        toast.view!!.findViewById<TextView>(android.R.id.message)
            .setTextColor(context.getColor(R.color.white))
        toast.show()
    }


    fun md5(src: String): String {
        val md5: MessageDigest = MessageDigest.getInstance("MD5")
        // Получить зашифрованный байтовый массив
        val bytes: ByteArray = md5.digest(src.toByteArray())
        val result = StringBuilder()
        // Преобразование байтового массива в шестнадцатеричную строку
        for (b in bytes) {
            // 1 байт равен 8 битам, а один шестнадцатеричный код (16) равен 16 битам, поэтому один байт может быть представлен 2 шестнадцатеричным
            var temp = Integer.toHexString(0xff and b.toInt())
            // Менее 2 длин дополнены 0
            if (temp.length == 1) {
                temp = "0$temp"
            }
            result.append(temp)
        }
        // Возвращаем последнюю строку
        return result.toString()
    }

    fun encrypt(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}