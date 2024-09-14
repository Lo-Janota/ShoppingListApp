package com.example.shoppinglistapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // Encontrar o botão flutuante
        val fabAddList = findViewById<FloatingActionButton>(R.id.fab_add_list)

        // Configurar o clique do botão flutuante
        fabAddList.setOnClickListener {
            // Ação ao clicar no botão flutuante
            Toast.makeText(this, "Adicionar nova lista", Toast.LENGTH_SHORT).show()
        }
    }
}
