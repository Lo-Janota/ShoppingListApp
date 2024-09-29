// ItemListActivity.kt
package com.example.shoppinglistapp

import Item
import ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemListActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter // Adapter da RecyclerView
    private val items = mutableListOf<Item>()     // Lista de itens

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        // Inicializando a RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(items)
        recyclerView.adapter = itemAdapter

        // Botão para adicionar novos itens
        val addItemButton = findViewById<Button>(R.id.btn_add_item)
        addItemButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, 1) // Iniciar AddItemActivity para adicionar um novo item
        }

        // Adicionar lógica para receber os dados da lista de compras e exibir os itens aqui
    }

    // Receber o novo item da AddItemActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val newItem = data?.getParcelableExtra<Item>("NEW_ITEM")
            if (newItem != null) {
                items.add(newItem)
                items.sortBy { it.name } // Ordena alfabeticamente
                itemAdapter.notifyDataSetChanged()
            }
        }
    }
}
