// EditItemActivity.kt
package com.example.shoppinglistapp

import Item
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditItemActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var unitEditText: EditText
    private var itemPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        nameEditText = findViewById(R.id.edit_item_name)
        quantityEditText = findViewById(R.id.edit_item_quantity)
        unitEditText = findViewById(R.id.edit_item_unit)

        // Receber o item a ser editado
        val item = intent.getParcelableExtra<Item>("ITEM")
        itemPosition = intent.getIntExtra("POSITION", -1)

        // Preencher os campos com os dados do item
        nameEditText.setText(item?.name)
        quantityEditText.setText(item?.quantity.toString())
        unitEditText.setText(item?.unit)

        val saveButton = findViewById<Button>(R.id.btn_save_item)
        saveButton.setOnClickListener {
            // Verifica se a posição é válida antes de criar o item editado
            if (itemPosition >= 0) {
                val editedItem = item?.copy(
                    name = nameEditText.text.toString(),
                    quantity = quantityEditText.text.toString().toIntOrNull() ?: 1,
                    unit = unitEditText.text.toString()
                )

                // Passa o item editado e sua posição de volta
                val resultIntent = Intent().apply {
                    putExtra("EDITED_ITEM", editedItem)
                    putExtra("POSITION", itemPosition)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
