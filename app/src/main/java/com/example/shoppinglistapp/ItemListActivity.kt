// ItemListActivity.kt
package com.example.shoppinglistapp

import Item
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ItemListActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var shoppingList: MutableList<Item>
    private val REQUEST_EDIT_ITEM = 1

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        // Inicializando a lista de compras e o Adapter
        shoppingList = mutableListOf() // Inicialize sua lista de compras
        itemAdapter = ItemAdapter(groupItems(shoppingList))

        val sharedPreferences = getSharedPreferences("ShoppingListApp", MODE_PRIVATE)
        val savedListJson = sharedPreferences.getString("SAVED_LIST", null)

        if (savedListJson != null) {
            val gson = Gson()
            val itemType = object : TypeToken<List<Item>>() {}.type
            shoppingList = gson.fromJson(savedListJson, itemType)
        } else {
            shoppingList = mutableListOf()
        }

        itemAdapter = ItemAdapter(groupItems(shoppingList))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_items)
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Botão para adicionar novos itens
        val addItemButton = findViewById<ImageButton>(R.id.btn_add_item)
        addItemButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Botão para editar itens
        val editButton = findViewById<ImageButton>(R.id.btn_edit_item)
        editButton.setOnClickListener {
            val selectedItem = itemAdapter.getSelectedItem() // Método para obter o item selecionado
            val position = itemAdapter.getSelectedItemPosition() // Método para obter a posição do item selecionado

            if (selectedItem != null && position >= 0) {
                val intent = Intent(this, EditItemActivity::class.java).apply {
                    putExtra("ITEM", selectedItem)
                    putExtra("POSITION", position)
                }
                startActivityForResult(intent, REQUEST_EDIT_ITEM)
            }
        }

        // Botão para excluir itens
        val deleteButton = findViewById<ImageButton>(R.id.btn_delete_item)
        deleteButton.setOnClickListener {
            val position = itemAdapter.getSelectedItemPosition() // Método para obter a posição do item selecionado
            if (position >= 0) {
                // Encontrar o item selecionado
                val selectedItem = itemAdapter.getSelectedItem()
                if (selectedItem != null) {
                    shoppingList.remove(selectedItem) // Remove o item da lista
                    itemAdapter.updateItems(groupItems(shoppingList)) // Atualiza os itens agrupados
                    itemAdapter.notifyItemRemoved(position) // Notifica que um item foi removido
                    itemAdapter.notifyDataSetChanged() // Em caso de que a estrutura de dados tenha mudado
                }
            }
        }

        // Botão para voltar
        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        // Exibir o nome da lista
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
                shoppingList.add(newItem)
                // Ordenar os itens pelo nome
                shoppingList.sortBy { it.name }

                // Agrupar itens por categoria
                val groupedItems = groupItems(shoppingList)
                itemAdapter.updateItems(groupedItems) // Atualiza os itens agrupados
                itemAdapter.notifyItemInserted(shoppingList.size - 1) // Notifica que um novo item foi inserido

                // Log para depuração
                Log.d("ShoppingList", "Item added: ${newItem.name}, Total items: ${shoppingList.size}")
            }
        }

        if (requestCode == REQUEST_EDIT_ITEM && resultCode == Activity.RESULT_OK) {
            val editedItem = data?.getParcelableExtra<Item>("EDITED_ITEM")
            val position = data?.getIntExtra("POSITION", -1) ?: -1

            if (position >= 0 && editedItem != null) {
                shoppingList[position] = editedItem // Atualiza o item na lista
                itemAdapter.updateItems(groupItems(shoppingList)) // Atualiza os itens no adaptador
                itemAdapter.notifyDataSetChanged() // Notifica que os dados mudaram
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("ShoppingListApp", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Converter a lista de itens para JSON (exemplo usando Gson)
        val gson = Gson()
        val jsonList = gson.toJson(shoppingList)

        editor.putString("SAVED_LIST", jsonList)
        editor.apply()
    }


    // Método para agrupar itens por categoria
    private fun groupItems(items: List<Item>): Map<String, List<Item>> {
        return items.groupBy { it.category }
    }
}
