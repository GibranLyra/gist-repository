package gibran.com.br.githubservice.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Files(
        @PrimaryKey(autoGenerate = true)
       val id: String,
        var file: String? = null)
