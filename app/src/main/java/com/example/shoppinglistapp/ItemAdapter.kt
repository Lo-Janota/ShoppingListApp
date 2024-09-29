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

    override fun getItemCount(): Int = items.size
}
