// ItemAdapter.kt
package com.example.shoppinglistapp

import Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var groupedItems: Map<String, List<Item>>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.item_category_icon)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
        val itemUnit: TextView = itemView.findViewById(R.id.item_unit)
        val itemPurchasedCheckBox: CheckBox = itemView.findViewById(R.id.item_purchased_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Obter a lista de categorias
        val categoryKeys = groupedItems.keys.toList()
        val totalItems = groupedItems.values.flatten()

        if (position < totalItems.size) {
            val item = totalItems[position] // Obtenha o item direto da lista plana

            holder.itemName.text = item.name
            holder.itemQuantity.text = item.quantity.toString()
            holder.itemUnit.text = item.unit

            // Definir ícone de categoria
            when (item.category) {
                "Alimentos" -> holder.categoryIcon.setImageResource(R.drawable.ic_alimentos)
                "Frutas/Verduras/Legumes" -> holder.categoryIcon.setImageResource(R.drawable.ic_frutas)
                "Carnes" -> holder.categoryIcon.setImageResource(R.drawable.ic_carnes)
                "Bebidas" -> holder.categoryIcon.setImageResource(R.drawable.ic_bebidas)
                "Frios" -> holder.categoryIcon.setImageResource(R.drawable.ic_frios)
                "Padaria" -> holder.categoryIcon.setImageResource(R.drawable.ic_padaria)
                "Higiene" -> holder.categoryIcon.setImageResource(R.drawable.ic_higiene)
                "Limpeza" -> holder.categoryIcon.setImageResource(R.drawable.ic_limpeza)
                "Outros" -> holder.categoryIcon.setImageResource(R.drawable.ic_default_category)
                else -> holder.categoryIcon.setImageResource(R.drawable.ic_default_category)
            }
        }
    }


    // Método para atualizar a lista de itens
    fun updateItems(newGroupedItems: Map<String, List<Item>>) {
        groupedItems = newGroupedItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        // Contar o total de itens agrupados
        return groupedItems.values.flatten().size
    }
}
