package com.example.shoppinglistapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var imageViewItem: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val editTextName = findViewById<EditText>(R.id.edit_text_name)
        val editTextQuantity = findViewById<EditText>(R.id.edit_text_quantity)
        val editTextUnit = findViewById<EditText>(R.id.edit_text_unit)
        val editTextCategory = findViewById<EditText>(R.id.edit_text_category)
        val buttonSelectImage = findViewById<Button>(R.id.button_select_image)
        imageViewItem = findViewById(R.id.image_view_item)
        val buttonAdd = findViewById<Button>(R.id.button_add)

        // Ação para selecionar a imagem da galeria
        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2) // Código de solicitação 2 para imagem
        }

        // Ação para adicionar o item
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val quantity = editTextQuantity.text.toString()
            val unit = editTextUnit.text.toString()
            val category = editTextCategory.text.toString()

            if (name.isNotEmpty() && quantity.isNotEmpty() && unit.isNotEmpty() && category.isNotEmpty()) {
                // Retornar os dados para a HomeActivity
                val resultIntent = Intent()
                resultIntent.putExtra("ITEM_NAME", name)
                resultIntent.putExtra("ITEM_QUANTITY", quantity)
                resultIntent.putExtra("ITEM_UNIT", unit)
                resultIntent.putExtra("ITEM_CATEGORY", category)
                resultIntent.putExtra("ITEM_IMAGE_URI", selectedImageUri.toString()) // Passar o URI da imagem como string
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Finalizar a activity e voltar para a HomeActivity
            }
        }
    }

    // Receber a imagem selecionada da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                selectedImageUri = imageUri
                imageViewItem.setImageURI(imageUri)
            }
        }
    }
}
