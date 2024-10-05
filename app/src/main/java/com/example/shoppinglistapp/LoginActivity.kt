// LoginActivity.kt
package com.example.shoppinglistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener { handleLogin() }
        btnRegister.setOnClickListener { navigateToRegister() }
    }

    private fun handleLogin() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!isValidEmail(email)) {
            emailInput.error = "Email inválido!"
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Campo obrigatório!"
            return
        }

        if (areCredentialsValid(email, password)) {
            Toast.makeText(this, "Login efetuado com sucesso...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun areCredentialsValid(email: String, password: String): Boolean {
        val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("USER_EMAIL", "")
        val savedPassword = sharedPreferences.getString("USER_PASSWORD", "")
        return email == savedEmail && password == savedPassword
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
