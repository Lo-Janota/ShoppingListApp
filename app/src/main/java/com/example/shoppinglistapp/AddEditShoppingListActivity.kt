package com.example.shoppinglistapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglistapp.databinding.ActivityEditListBinding

class AddEditShoppingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditListBinding
    private var selectedImageUri: Uri? = null
    private var listPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica se está em modo de edição
        val isEditMode = intent.getBooleanExtra("EDIT_MODE", false)
        if (isEditMode) {
            listPosition = intent.getIntExtra("LIST_POSITION", -1)
            val title = intent.getStringExtra("LIST_TITLE")
            val imageUriString = intent.getStringExtra("LIST_IMAGE_URI")
            binding.editTextTitle.setText(title)
            selectedImageUri = Uri.parse(imageUriString)
            binding.imageViewSelected.setImageURI(selectedImageUri)
        }

        // Selecionar imagem da galeria
        binding.buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }

        // Salvar lista de compras
        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            if (title.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("LIST_TITLE", title)
                    putExtra("LIST_IMAGE_URI", selectedImageUri?.toString())
                    putExtra("EDIT_MODE", isEditMode)
                    putExtra("LIST_POSITION", listPosition)
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
            binding.imageViewSelected.setImageURI(selectedImageUri)
        }
    }
}
