package com.example.shoppinglistapp

import android.net.Uri

data class ShoppingList(
    val title: String,
    val imageUri: Uri? = null,
)
