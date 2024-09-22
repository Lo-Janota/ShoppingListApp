import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.ShoppingList

class ShoppingListAdapter(
    private val shoppingLists: MutableList<ShoppingList> // Use MutableList para permitir exclusão
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    private var itemClickListener: ((Int) -> Unit)? = null
    private var deleteClickListener: ((Int) -> Unit)? = null

    class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val deleteButton: ImageView = itemView.findViewById(R.id.btn_delete) // Adicione isso
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shoppingList = shoppingLists[position]
        holder.titleTextView.text = shoppingList.title

        if (shoppingList.imageUri != null) {
            holder.imageView.setImageURI(shoppingList.imageUri)
        } else {
            holder.imageView.setImageResource(R.drawable.ic_placeholder) // Imagem de placeholder
        }

        // Configurar o clique do item
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position)
        }

        // Configurar o clique do botão de exclusão
        holder.deleteButton.setOnClickListener {
            deleteClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = shoppingLists.size

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (Int) -> Unit) {
        deleteClickListener = listener
    }

    // Método para remover uma lista
    fun removeItem(position: Int) {
        shoppingLists.removeAt(position)
        notifyItemRemoved(position)
    }
}
