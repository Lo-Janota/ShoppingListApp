package com.example.shoppinglistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

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

            // Verifica se o campo de email está preenchido e válido
            if (emailInput.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.error = "Email inválido"
                return@setOnClickListener
            }

            // Verifica se o campo de senha está preenchido
            if (passwordInput.isEmpty()) {
                password.error = "Campo obrigatório"
                return@setOnClickListener
            }

            // Verifica se o usuário já está registrado no SharedPreferences
            val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("USER_EMAIL", "")
            val savedPassword = sharedPreferences.getString("USER_PASSWORD", "")

            // Verifica se o email e senha estão corretos
            if (emailInput == savedEmail && passwordInput == savedPassword) {
                // Navega para a HomeActivity se o login for bem-sucedido
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish() // Fecha a tela de login
            } else {
                // Exibe uma mensagem de erro se as credenciais não estiverem corretas
                Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
