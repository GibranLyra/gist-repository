package gibran.com.br.githubservice.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Files(
        @PrimaryKey(autoGenerate = true)
       val id: Int,
        @Embedded(prefix = "files_file_")
        var file: File? = null)
