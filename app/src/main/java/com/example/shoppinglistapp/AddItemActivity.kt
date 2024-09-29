// AddItemActivity.kt
package com.example.shoppinglistapp

import Item
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var categoryIcon: ImageView
    private var selectedCategoryIcon: Int = R.drawable.ic_default_category // Ícone padrão

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        nameEditText = findViewById(R.id.edit_item_name)
        quantityEditText = findViewById(R.id.edit_item_quantity)
        unitEditText = findViewById(R.id.edit_item_unit)
        categoryIcon = findViewById(R.id.item_category_icon)

        // Selecione o ícone de categoria (para simplificar, apenas mudaremos a imagem diretamente)
        categoryIcon.setOnClickListener {
            // Aqui você pode implementar um seletor de ícones ou usar imagens fixas
            selectedCategoryIcon = R.drawable.ic_selected_category
            categoryIcon.setImageResource(selectedCategoryIcon)
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
                categoryIconResId = selectedCategoryIcon,
                isPurchased = false
            )

            // Enviar o item de volta para ItemListActivity
            val resultIntent = Intent()
            resultIntent.putExtra("NEW_ITEM", newItem)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Fechar a AddItemActivity e voltar para a ItemListActivity
        }
    }
}
