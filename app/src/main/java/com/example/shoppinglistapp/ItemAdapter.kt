import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.item_category_icon)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemQuantity: TextView = itemView.findViewById(R.id.item_quantity)
        val itemUnit: TextView = itemView.findViewById(R.id.item_unit)
        val itemCategoryIcon: ImageView = itemView.findViewById(R.id.item_category_icon)
        val itemPurchasedCheckBox: CheckBox = itemView.findViewById(R.id.item_purchased_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemQuantity.text = item.quantity.toString()
        holder.itemUnit.text = item.unit

        when (item.category) {
            "Frutas" -> holder.categoryIcon.setImageResource(R.drawable.ic_fruits)
            "Carnes" -> holder.categoryIcon.setImageResource(R.drawable.ic_meats)
            "Bebidas" -> holder.categoryIcon.setImageResource(R.drawable.ic_drinks)
            // Adicione mais categorias conforme necessário
            else -> holder.categoryIcon.setImageResource(R.drawable.ic_placeholder) // Ícone padrão
        }
    }

    override fun getItemCount(): Int = items.size
}
