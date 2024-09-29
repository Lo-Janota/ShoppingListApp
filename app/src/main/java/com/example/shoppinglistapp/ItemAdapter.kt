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
            itemPurchasedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                // Tente atualizar o item e notifique a mudança
                try {
                    val item = getItem(adapterPosition)
                    item?.let {
                        it.isPurchased = isChecked

                        // Atualizar a lista
                        val newGroupedItems = groupedItems.toMutableMap()
                        newGroupedItems[it.category] = newGroupedItems[it.category]?.map { existingItem ->
                            if (existingItem == it) it else existingItem
                        } ?: listOf(it)

                        updateItems(newGroupedItems)
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // Log de erro
                }
            }

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
        val (nonPurchasedItems, purchasedItems) = getItemsSeparated()
        val itemList = if (position < nonPurchasedItems.size) {
            nonPurchasedItems
        } else {
            purchasedItems
        }

        val currentItem = if (position < nonPurchasedItems.size) {
            nonPurchasedItems[position]
        } else {
            purchasedItems[position - nonPurchasedItems.size]
        }

        holder.itemName.text = currentItem.name
        holder.itemQuantity.text = currentItem.quantity.toString()
        holder.itemUnit.text = currentItem.unit
        holder.itemPurchasedCheckBox.isChecked = currentItem.isPurchased // Setando estado do checkbox

        // Definir ícone de categoria
        when (currentItem.category) {
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

        // Marcar o item selecionado
        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Altere a cor ou estilo conforme desejado
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    private fun getItemsSeparated(): Pair<List<Item>, List<Item>> {
        val nonPurchasedItems = mutableListOf<Item>()
        val purchasedItems = mutableListOf<Item>()

        for (items in groupedItems.values) {
            for (item in items) {
                if (item.isPurchased) {
                    purchasedItems.add(item)
                } else {
                    nonPurchasedItems.add(item)
                }
            }
        }
        return Pair(nonPurchasedItems, purchasedItems)
    }

    private fun getItem(position: Int): Item? {
        val (nonPurchasedItems, purchasedItems) = getItemsSeparated()
        return when {
            position < nonPurchasedItems.size -> nonPurchasedItems[position]
            position < nonPurchasedItems.size + purchasedItems.size -> purchasedItems[position - nonPurchasedItems.size]
            else -> null
        }
    }

    fun getSelectedItem(): Item? {
        return getItem(selectedItemPosition)
    }

    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }

    fun updateItems(newItems: Map<String, List<Item>>) {
        groupedItems = newItems
        notifyDataSetChanged() // Notifica que os dados mudaram
    }

    override fun getItemCount(): Int {
        val (nonPurchasedItems, purchasedItems) = getItemsSeparated()
        return nonPurchasedItems.size + purchasedItems.size
    }
}
