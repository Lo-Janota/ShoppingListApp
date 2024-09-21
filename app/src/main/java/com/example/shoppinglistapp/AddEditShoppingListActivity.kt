package com.example.shoppinglistapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddEditShoppingListActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var imageViewSelected: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_shopping_list)

        editTextTitle = findViewById(R.id.edit_text_title)
        imageViewSelected = findViewById(R.id.image_view_selected)
        val buttonSelectImage = findViewById<Button>(R.id.button_select_image)
        val buttonSave = findViewById<Button>(R.id.button_save)

        // Selecionar imagem da galeria
        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }

        // Salvar lista de compras
        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            if (title.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("LIST_TITLE", title)
                    putExtra("LIST_IMAGE_URI", selectedImageUri?.toString())
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    // Receber a imagem selecionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imageViewSelected.setImageURI(selectedImageUri)
        }
    }
}
