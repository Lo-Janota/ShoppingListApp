package com.example.shoppinglistapp

import Item
import android.net.Uri

data class ShoppingList(
    val title: String,
    val imageUri: Uri? = null,
    val items: MutableList<Item> = mutableListOf(),
)
