package gibran.com.br.githubservice.model

data class GistHistory(var committedAt: String? = null,
                       var changeStatus: GistHistoryChangeStatus? = null,
                       var version: String? = null,
                       var user: Any? = null,
                       var url: String? = null)
