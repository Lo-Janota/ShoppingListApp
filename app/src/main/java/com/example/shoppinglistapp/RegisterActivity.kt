package com.example.shoppinglistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirm_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            val nameInput = name.text.toString()
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()
            val confirmPasswordInput = confirmPassword.text.toString()

            if (nameInput.isEmpty() || emailInput.isEmpty() || passwordInput.isEmpty() || confirmPasswordInput.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordInput != confirmPasswordInput) {
                Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se tudo estiver correto, você pode prosseguir com o registro
            Toast.makeText(this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show()

            // Inicie LoginActivity após o registro
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
