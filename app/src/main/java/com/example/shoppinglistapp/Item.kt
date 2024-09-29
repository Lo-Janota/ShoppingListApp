import android.os.Parcel
import android.os.Parcelable

data class Item(
    val name: String,
    val quantity: Int,
    val unit: String,
    val categoryIconResId: Int, // Resource ID do ícone da categoria
    var isPurchased: Boolean // Flag se o item foi comprado ou não
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(quantity)
        parcel.writeString(unit)
        parcel.writeInt(categoryIconResId)
        parcel.writeByte(if (isPurchased) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item = Item(parcel)
        override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
    }
}
