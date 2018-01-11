package gibran.com.br.githubservice.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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
        var truncated: Boolean = false)