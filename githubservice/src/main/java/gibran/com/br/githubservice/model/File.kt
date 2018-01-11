package gibran.com.br.githubservice.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class File(@PrimaryKey(autoGenerate = true)
                val id: Int,
                var size: Int = 0,
                var truncated: Boolean = false,
                var language: String? = null,
                var type: String? = null,
                var rawUrl: String? = null,
                var content: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(size)
        parcel.writeByte(if (truncated) 1 else 0)
        parcel.writeString(language)
        parcel.writeString(type)
        parcel.writeString(rawUrl)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<File> {
        override fun createFromParcel(parcel: Parcel): File {
            return File(parcel)
        }

        override fun newArray(size: Int): Array<File?> {
            return arrayOfNulls(size)
        }
    }
}
