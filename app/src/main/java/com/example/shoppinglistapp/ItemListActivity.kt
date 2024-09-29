// ItemListActivity.kt
package com.example.shoppinglistapp

import Item
import ItemAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemListActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private val items = mutableListOf<Item>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        // Inicializando a RecyclerView e o Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_items)
        itemAdapter = ItemAdapter(items) // Usando a lista correta 'items'
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Botão para adicionar novos itens
        val addItemButton = findViewById<ImageButton>(R.id.btn_add_item)
        addItemButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val editButton = findViewById<ImageButton>(R.id.btn_edit_item)
        editButton.setOnClickListener {
            // Ação para editar item selecionado
        }

        val deleteButton = findViewById<ImageButton>(R.id.btn_delete_item)
        deleteButton.setOnClickListener {
            // Ação para excluir item selecionado
        }

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val listName = intent.getStringExtra("SHOPPING_LIST_TITLE")
        val listNameTextView = findViewById<TextView>(R.id.tv_list_name)
        listNameTextView.text = listName ?: "Nome da Lista"
    }

    // Receber o novo item da AddItemActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val newItem = data?.getParcelableExtra<Item>("NEW_ITEM")
            if (newItem != null) {
                items.add(newItem)
                items.sortBy { it.name } // Ordena os itens pelo nome
                itemAdapter.notifyDataSetChanged() // Atualiza o Adapter com a nova lista
            }
        }
    }
}

