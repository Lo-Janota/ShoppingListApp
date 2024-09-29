package com.example.shoppinglistapp

import Item
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var groupedItems: Map<String, List<Item>>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var selectedItemPosition: Int = -1

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.item_category_icon)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
        val itemUnit: TextView = itemView.findViewById(R.id.item_unit)
        val itemPurchasedCheckBox: CheckBox = itemView.findViewById(R.id.item_purchased_checkbox)

        init {
            itemView.setOnClickListener {
                selectedItemPosition = adapterPosition
                notifyDataSetChanged() // Para atualizar a seleção
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val allItems = groupedItems.values.flatten() // Lista de todos os itens
        if (position < allItems.size) {
            val item = allItems[position]
            holder.itemName.text = item.name
            holder.itemQuantity.text = item.quantity.toString()
            holder.itemUnit.text = item.unit

            // Definir ícone de categoria
            holder.categoryIcon.setImageResource(
                when (item.category) {
                    "Alimentos" -> R.drawable.ic_alimentos
                    "Frutas/Verduras/Legumes" -> R.drawable.ic_frutas
                    "Carnes" -> R.drawable.ic_carnes
                    "Bebidas" -> R.drawable.ic_bebidas
                    "Frios" -> R.drawable.ic_frios
                    "Padaria" -> R.drawable.ic_padaria
                    "Higiene" -> R.drawable.ic_higiene
                    "Limpeza" -> R.drawable.ic_limpeza
                    "Outros" -> R.drawable.ic_default_category
                    else -> R.drawable.ic_default_category
                }
            )

            // Marcar o item selecionado
            holder.itemView.setBackgroundColor(
                if (selectedItemPosition == position) Color.LTGRAY else Color.WHITE
            )
        }
    }

    fun getSelectedItem(): Item? {
        val allItems = groupedItems.values.flatten() // Lista de todos os itens
        return if (selectedItemPosition >= 0 && selectedItemPosition < allItems.size) {
            allItems[selectedItemPosition]
        } else null
    }

    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }

    fun updateItems(newItems: Map<String, List<Item>>) {
        groupedItems = newItems
        notifyDataSetChanged() // Notifica que os dados mudaram
    }

    override fun getItemCount(): Int {
        return groupedItems.values.flatten().size
    }
}
