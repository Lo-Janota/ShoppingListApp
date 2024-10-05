package com.example.shoppinglistapp

import Item
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.databinding.ActivityItemListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemListActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var shoppingList: ShoppingList
    private lateinit var binding: ActivityItemListBinding
    private val REQUEST_EDIT_ITEM = 1

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando a lista de compras com seu título
        val listTitle = intent.getStringExtra("SHOPPING_LIST_TITLE") ?: "Nova Lista"
        shoppingList = ShoppingList(title = listTitle)

        // Carregar a lista de itens se existir
        val sharedPreferences = getSharedPreferences("ShoppingListApp", MODE_PRIVATE)
        val savedListJson = sharedPreferences.getString("SAVED_LIST_${shoppingList.title}", null)
        if (savedListJson != null) {
            val gson = Gson()
            val type = object : TypeToken<ShoppingList>() {}.type
            shoppingList = gson.fromJson(savedListJson, type)
        }

        // Inicializando o Adapter
        itemAdapter = ItemAdapter(groupItems(shoppingList.items))

        // Configurando RecyclerView
        binding.recyclerViewItems.adapter = itemAdapter
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)

        // Botão para adicionar novos itens
        binding.btnAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Botão para editar itens
        binding.btnEditItem.setOnClickListener {
            val selectedItem = itemAdapter.getSelectedItem()
            val position = itemAdapter.getSelectedItemPosition()

            if (selectedItem != null && position >= 0) {
                val intent = Intent(this, EditItemActivity::class.java).apply {
                    putExtra("ITEM", selectedItem)
                    putExtra("POSITION", position)
                }
                startActivityForResult(intent, REQUEST_EDIT_ITEM)
            }
        }

        // Botão para excluir itens
        binding.btnDeleteItem.setOnClickListener {
            val position = itemAdapter.getSelectedItemPosition()
            if (position >= 0) {
                // Encontrar o item selecionado
                val selectedItem = itemAdapter.getSelectedItem()
                if (selectedItem != null) {
                    shoppingList.items.remove(selectedItem) // Remove o item da lista
                    itemAdapter.updateItems(groupItems(shoppingList.items))
                    itemAdapter.notifyItemRemoved(position)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }

        // Botão para voltar
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Exibir o nome da lista
        binding.tvListName.text = shoppingList.title
    }

    // Receber o novo item da AddItemActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val newItem = data?.getParcelableExtra<Item>("NEW_ITEM")
            if (newItem != null) {
                shoppingList.items.add(newItem)
                shoppingList.items.sortBy { it.name } // Ordenar os itens pelo nome
                itemAdapter.updateItems(groupItems(shoppingList.items))
                itemAdapter.notifyItemInserted(shoppingList.items.size - 1)

                // Log para depuração
                Log.d("ShoppingList", "Item added: ${newItem.name}, Total items: ${shoppingList.items.size}")
            }
        }

        if (requestCode == REQUEST_EDIT_ITEM && resultCode == Activity.RESULT_OK) {
            val editedItem = data?.getParcelableExtra<Item>("EDITED_ITEM")
            val position = data?.getIntExtra("POSITION", -1) ?: -1

            if (position >= 0 && editedItem != null) {
                shoppingList.items[position] = editedItem // Atualiza o item na lista
                itemAdapter.updateItems(groupItems(shoppingList.items))
                itemAdapter.notifyDataSetChanged() // Notifica que os dados mudaram
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("ShoppingListApp", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Salvar a shoppingList como JSON
        val gson = Gson()
        val jsonList = gson.toJson(shoppingList)

        editor.putString("SAVED_LIST_${shoppingList.title}", jsonList)
        editor.apply()
    }

    // Método para agrupar itens por categoria
    private fun groupItems(items: List<Item>): Map<String, List<Item>> {
        return items.groupBy { it.category }
    }
}
