package com.example.shoppinglistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnLogin.setOnClickListener {
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()

            if (emailInput.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.error = "Email inválido"
                return@setOnClickListener
            }

            if (passwordInput.isEmpty()) {
                password.error = "Campo obrigatório"
                return@setOnClickListener
            }

            // Login logic
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
