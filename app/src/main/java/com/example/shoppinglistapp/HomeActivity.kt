package com.example.shoppinglistapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ShoppingListAdapter
    private val shoppingLists = mutableListOf<ShoppingList>() // Lista de compras

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializando RecyclerView com GridLayoutManager
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_lists)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 colunas
        adapter = ShoppingListAdapter(shoppingLists)
        recyclerView.adapter = adapter

        // Encontrar os botões
        val fabAddList = findViewById<FloatingActionButton>(R.id.fab_add_list)
        val fabLogout = findViewById<FloatingActionButton>(R.id.fab_logout)
        val fabSearch = findViewById<FloatingActionButton>(R.id.fab_search)

        fabLogout.setOnClickListener {
            Toast.makeText(this, "Saindo...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        fabSearch.setOnClickListener {
            Toast.makeText(this, "Pesquisar", Toast.LENGTH_SHORT).show()
        }

        fabAddList.setOnClickListener {
            // Abrir AddEditShoppingListActivity para adicionar uma nova lista de compras
            val intent = Intent(this@HomeActivity, AddEditShoppingListActivity::class.java)
            startActivityForResult(intent, 1) // Request code 1
        }
    }

    // Receber a nova lista de compras adicionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val title = data?.getStringExtra("LIST_TITLE")
            val imageUriString = data?.getStringExtra("LIST_IMAGE_URI")
            val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }

            if (title != null) {
                // Adicionar nova lista de compras e atualizar RecyclerView
                val newList = ShoppingList(title, imageUri)
                shoppingLists.add(newList)
                shoppingLists.sortBy { it.title } // Ordenar alfabeticamente
                adapter.notifyDataSetChanged()
            }
        }
    }
}
