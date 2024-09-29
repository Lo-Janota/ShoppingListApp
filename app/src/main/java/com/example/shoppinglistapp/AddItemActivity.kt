// AddItemActivity.kt
package com.example.shoppinglistapp

import Item
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var categoryIcon: ImageView
    private var selectedCategory: String = "Categoria Desconhecida" // Inicializando com uma categoria padrão

    companion object {
        val shoppingList = mutableListOf<Item>()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        nameEditText = findViewById(R.id.edit_item_name)
        quantityEditText = findViewById(R.id.edit_item_quantity)
        unitEditText = findViewById(R.id.edit_item_unit)
        categoryIcon = findViewById(R.id.item_category_icon)
        val categorySpinner = findViewById<Spinner>(R.id.category_spinner)

        val categories = listOf("Frutas", "Carnes", "Bebidas")
        val icons = listOf(R.drawable.ic_fruits, R.drawable.ic_meats, R.drawable.ic_drinks)

        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, categories) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.spinner_item, parent, false)
                val icon = view.findViewById<ImageView>(R.id.spinner_icon)
                val text = view.findViewById<TextView>(R.id.spinner_text)

                // Definindo o ícone corretamente
                if (position in icons.indices) {
                    icon.setImageResource(icons[position])
                }

                text.text = categories[position]
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return getView(position, convertView, parent)
            }
        }
        categorySpinner.adapter = adapter

        // Defina o que acontece quando a categoria é selecionada
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position] // Armazena a categoria selecionada
                categoryIcon.setImageResource(icons[position]) // Muda o ícone da categoria
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Você pode deixar vazio ou definir um comportamento padrão
            }
        }

        // Botão de salvar o item
        val saveButton = findViewById<Button>(R.id.btn_save_item)
        saveButton.setOnClickListener {
            val itemName = nameEditText.text.toString()
            val itemQuantity = quantityEditText.text.toString().toIntOrNull() ?: 1
            val itemUnit = unitEditText.text.toString()

            // Criar um novo item
            val newItem = Item(
                name = itemName,
                quantity = itemQuantity,
                unit = itemUnit,
                category = selectedCategory, // Use o nome da categoria
                isPurchased = false
            )
            shoppingList.add(newItem) // Adicione o item na lista de compras

            val resultIntent = Intent()
            resultIntent.putExtra("NEW_ITEM", newItem)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Fecha a Activity
        }

        val cancelButton = findViewById<Button>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
