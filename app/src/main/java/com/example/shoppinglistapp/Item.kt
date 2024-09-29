import android.os.Parcel
import android.os.Parcelable

data class Item(
    val name: String,
    val quantity: Int,
    val unit: String,
    val category: String,
    var isPurchased: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Lê o nome
        parcel.readInt(),          // Lê a quantidade
        parcel.readString() ?: "", // Lê a unidade
        parcel.readString() ?: "", // Lê a categoria
        parcel.readByte() != 0.toByte() // Lê se foi comprado
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)       // Escreve o nome
        parcel.writeInt(quantity)      // Escreve a quantidade
        parcel.writeString(unit)       // Escreve a unidade
        parcel.writeString(category)    // Escreve a categoria
        parcel.writeByte(if (isPurchased) 1 else 0) // Escreve se foi comprado
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item = Item(parcel)
        override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
    }
}
