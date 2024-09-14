package com.example.shoppinglistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

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

            // Verificação se todos os campos estão preenchidos
            if (nameInput.isEmpty() || emailInput.isEmpty() || passwordInput.isEmpty() || confirmPasswordInput.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificação se a senha e a confirmação de senha são iguais
            if (passwordInput != confirmPasswordInput) {
                Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Salvando os dados do usuário no SharedPreferences
            val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("USER_EMAIL", emailInput)
            editor.putString("USER_PASSWORD", passwordInput)
            editor.apply()

            // Exibir mensagem de sucesso
            Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()

            // Navega para a tela de Login após o registro
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Fecha a tela de registro
        }
    }
}
