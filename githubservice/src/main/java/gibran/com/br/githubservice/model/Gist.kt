package gibran.com.br.githubservice.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */
@Entity
data class Gist(
        @PrimaryKey(autoGenerate = true)
        var uId: Int,
        var url: String? = null,
        var forksUrl: String? = null,
        var commitsUrl: String? = null,
        @ColumnInfo(name = "id", index = true)
        val id: String,
        var gitPullUrl: String? = null,
        var gitPushUrl: String? = null,
        var htmUrl: String? = null,
        @Embedded(prefix = "gist_files_")
        var files: Files? = null,
        var isPublic: Boolean = false,
        var createdAt: String? = null,
        var updatedAt: String? = null,
        var description: String? = null,
        var comments: String? = null,
        var user: String? = null,
        var commentsUrl: String? = null,
        @Embedded(prefix = "gist_owner_")
        var owner: Owner? = null,
        var truncated: Boolean = false) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Files::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Owner::class.java.classLoader),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uId)
        parcel.writeString(url)
        parcel.writeString(forksUrl)
        parcel.writeString(commitsUrl)
        parcel.writeString(id)
        parcel.writeString(gitPullUrl)
        parcel.writeString(gitPushUrl)
        parcel.writeString(htmUrl)
        parcel.writeParcelable(files, flags)
        parcel.writeByte(if (isPublic) 1 else 0)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(description)
        parcel.writeString(comments)
        parcel.writeString(user)
        parcel.writeString(commentsUrl)
        parcel.writeParcelable(owner, flags)
        parcel.writeByte(if (truncated) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Gist> {
        override fun createFromParcel(parcel: Parcel): Gist {
            return Gist(parcel)
        }

        override fun newArray(size: Int): Array<Gist?> {
            return arrayOfNulls(size)
        }
    }
}