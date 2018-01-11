package gibran.com.br.githubservice.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class File(@PrimaryKey(autoGenerate = true)
                val id: Int,
                var size: Int = 0,
                var truncated: Boolean = false,
                var language: String? = null,
                var type: String? = null,
                var rawUrl: String? = null,
                var content: String? = null)
