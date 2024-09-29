package com.example.shoppinglistapp

import ShoppingListAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ShoppingListAdapter
    private val shoppingLists = mutableListOf<ShoppingList>() // Lista de compras original
    private val filteredLists = mutableListOf<ShoppingList>() // Lista de compras filtrada
    private lateinit var searchEditText: EditText // Campo de pesquisa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializando os componentes
        searchEditText = findViewById(R.id.search_edit_text) // EditText para o campo de pesquisa
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_lists)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 colunas

        // Inicializando o adapter com as listas filtradas
        adapter = ShoppingListAdapter(filteredLists)
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

        // Implementação da funcionalidade de busca
        fabSearch.setOnClickListener {
            val query = searchEditText.text.toString().trim() // Obter o texto de pesquisa
            searchShoppingLists(query)
        }

        fabAddList.setOnClickListener {
            // Abrir AddEditShoppingListActivity para adicionar uma nova lista de compras
            val intent = Intent(this@HomeActivity, AddEditShoppingListActivity::class.java)
            startActivityForResult(intent, 1) // Request code 1
        }

        // Ao clicar em um item da lista, abrir para editar
        adapter.setOnItemClickListener { position ->
            val listToEdit = filteredLists[position]
            val intent = Intent(this@HomeActivity, AddEditShoppingListActivity::class.java).apply {
                putExtra("LIST_TITLE", listToEdit.title)
                putExtra("LIST_IMAGE_URI", listToEdit.imageUri.toString())
                putExtra("EDIT_MODE", true) // Indica que é modo de edição
                putExtra("LIST_POSITION", position) // Passa a posição da lista
            }
            startActivityForResult(intent, 1)
        }

        // Configurar o listener de exclusão
        adapter.setOnDeleteClickListener { position ->
            // Cria um AlertDialog para confirmação
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Você tem certeza que deseja excluir esta lista?")
                .setPositiveButton("Sim") { dialog, which ->
                    // Se o usuário confirmar, remove a lista
                    val listToRemove = filteredLists[position]
                    shoppingLists.remove(listToRemove) // Remove da lista original
                    filteredLists.removeAt(position) // Remove da lista filtrada
                    adapter.notifyItemRemoved(position) // Notifica o adaptador da remoção
                    adapter.notifyItemRangeChanged(position, filteredLists.size) // Atualiza os itens restantes
                }
                .setNegativeButton("Não") { dialog, which ->
                    dialog.dismiss() // Apenas fecha o diálogo
                }
                .create()

            alertDialog.show()
        }

        adapter.setOnViewListClickListener {
                position ->
            // Pegue a lista de compras selecionada
            val selectedList = shoppingLists[position]

            // Abra a Activity para ver os itens dessa lista
            val intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra("SHOPPING_LIST_TITLE", selectedList.title)
            startActivity(intent)
        }
    }

    // Função de busca
    private fun searchShoppingLists(query: String) {
        filteredLists.clear() // Limpar lista filtrada

        if (query.isNotEmpty()) {
            // Filtrar listas com base no título ou nos itens dentro da lista
            for (list in shoppingLists) {
                if (list.title.contains(query, ignoreCase = true)) {
                    filteredLists.add(list)
                }
            }
        } else {
            // Se o campo de busca estiver vazio, mostra todas as listas
            filteredLists.addAll(shoppingLists)
        }

        // Atualiza o RecyclerView com os resultados filtrados
        adapter.notifyDataSetChanged()
    }

    // Receber a nova lista de compras adicionada ou atualizada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val title = data?.getStringExtra("LIST_TITLE")
            val imageUriString = data?.getStringExtra("LIST_IMAGE_URI")
            val imageUri: Uri? = imageUriString?.let { Uri.parse(it) }

            if (title != null) {
                // Verifica se estamos editando uma lista existente
                val isEditMode = data.getBooleanExtra("EDIT_MODE", false)
                if (isEditMode) {
                    val position = data.getIntExtra("LIST_POSITION", -1)
                    if (position != -1) {
                        // Atualiza a lista existente
                        shoppingLists[position] = ShoppingList(title, imageUri)
                        filteredLists[position] = shoppingLists[position] // Atualiza na lista filtrada
                        // Reordena as listas após a edição
                        shoppingLists.sortBy { it.title }
                        filteredLists.sortBy { it.title }
                        adapter.notifyItemChanged(position)
                    }
                } else {
                    // Adicionar nova lista de compras
                    val newList = ShoppingList(title, imageUri)
                    shoppingLists.add(newList)
                    // Reordena as listas após a adição
                    shoppingLists.sortBy { it.title }
                    filteredLists.clear()
                    filteredLists.addAll(shoppingLists) // Atualiza a lista filtrada
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
