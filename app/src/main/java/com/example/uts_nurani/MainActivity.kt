package com.example.uts_nurani

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            val email: String = binding.InputEmail.text.toString().trim()
            val password: String = binding.InputPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.InputEmail.error = "Input Email"
                binding.InputEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.InputEmail.error = "Invalid email"
                binding.InputEmail.requestFocus()
                return@setOnClickListener
            }


            if (password.isEmpty() || password.length < 6) {
                binding.InputPassword.error = "Input Your Password"
                binding.InputPassword.requestFocus()
                return@setOnClickListener
            }


            loginUser(email, password)
        }

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //binding.txtForgotPassword.setOnclickListener{
        //  Intent(this, ResetPasswordActivity::class.java).also {
        //    startActivity(it)
        //}
        //}
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
            if (it.isSuccessful) {
                Intent(this, ContentActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this, ContentActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}