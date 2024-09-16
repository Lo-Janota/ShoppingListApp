package com.example.shoppinglistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Encontrar o botão
        val fabAddList = findViewById<FloatingActionButton>(R.id.fab_add_list)
        val fabLogout = findViewById<FloatingActionButton>(R.id.fab_logout)

        // Configurar o clique do botão
        fabAddList.setOnClickListener {
            Toast.makeText(this, "Adicionar nova lista", Toast.LENGTH_SHORT).show()
        }

        fabLogout.setOnClickListener {
            Toast.makeText(this, "Saindo...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
