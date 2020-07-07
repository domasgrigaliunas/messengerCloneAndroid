package com.kotlinmessenger.registerlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kotlinmessenger.R
import com.kotlinmessenger.messages.LatestMessagesActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_buttton_login.setOnClickListener {
            val email = email_editText_login.text.toString()
            val password = password_editText_login.text.toString()

            Log.d("Login", "Attempt login with email/password $email/***")

            // Need to fix
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d("LoginActivity", "Successfully logged in with uid: ${it.result?.user?.uid} ")

                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener() {
                    Log.d("LoginActivity", "Failed to login ${it.message} ")
                }

        }

        back_to_register_textView.setOnClickListener {
            Log.d("LoginActivity", "Close login activity ")
            finish()

        }

    }
    /*
    already_have_account_textView.setOnClickListener {
        Log.d("RegisterActivity", "Try to show login activity")

        // launch login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
     */




}