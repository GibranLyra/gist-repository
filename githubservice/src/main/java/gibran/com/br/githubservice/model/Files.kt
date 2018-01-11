package gibran.com.br.githubservice.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class Files(
        @PrimaryKey(autoGenerate = true)
       val id: Int,
        @Embedded(prefix = "files_file_")
        var file: File? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(File::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(file, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Files> {
        override fun createFromParcel(parcel: Parcel): Files {
            return Files(parcel)
        }

        override fun newArray(size: Int): Array<Files?> {
            return arrayOfNulls(size)
        }
    }
}
