package gibran.com.br.githubservice.model

data class GistFilesRing(var size: Int = 0,
                         var truncated: Boolean = false,
                         var language: String? = null,
                         var type: String? = null,
                         var rawUrl: String? = null,
                         var content: String? = null)
