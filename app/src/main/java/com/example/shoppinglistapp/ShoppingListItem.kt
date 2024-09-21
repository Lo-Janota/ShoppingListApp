package com.example.shoppinglistapp

import android.net.Uri

data class ShoppingListItem(
    val name: String,
    val quantity: String,
    val unit: String,
    val category: String,
    val imageUri: Uri?
)
