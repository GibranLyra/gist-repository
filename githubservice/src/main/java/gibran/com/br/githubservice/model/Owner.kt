package gibran.com.br.githubservice.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable


@Entity
data class Owner(
        @PrimaryKey(autoGenerate = true)
        var uId: Int,
        var gistsUrl: String? = null,
        var reposUrl: String? = null,
        var followingUrl: String? = null,
        var starredUrl: String? = null,
        var login: String? = null,
        var followersUrl: String? = null,
        var type: String? = null,
        var url: String? = null,
        var subscriptionsUrl: String? = null,
        var receivedEventsUrl: String? = null,
        var avatarUrl: String? = null,
        var eventsUrl: String? = null,
        var htmlUrl: String? = null,
        var siteAdmin: Boolean = false,
        var id: String?,
        var gravatarId: String? = null,
        var organizationsUrl: String? = null) : Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readByte() != 0.toByte(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(uId)
                parcel.writeString(gistsUrl)
                parcel.writeString(reposUrl)
                parcel.writeString(followingUrl)
                parcel.writeString(starredUrl)
                parcel.writeString(login)
                parcel.writeString(followersUrl)
                parcel.writeString(type)
                parcel.writeString(url)
                parcel.writeString(subscriptionsUrl)
                parcel.writeString(receivedEventsUrl)
                parcel.writeString(avatarUrl)
                parcel.writeString(eventsUrl)
                parcel.writeString(htmlUrl)
                parcel.writeByte(if (siteAdmin) 1 else 0)
                parcel.writeString(id)
                parcel.writeString(gravatarId)
                parcel.writeString(organizationsUrl)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Owner> {
                override fun createFromParcel(parcel: Parcel): Owner {
                        return Owner(parcel)
                }

                override fun newArray(size: Int): Array<Owner?> {
                        return arrayOfNulls(size)
                }
        }
}
