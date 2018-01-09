package gibran.com.br.githubservice.model

data class History(var committedAt: String? = null,
                   var changeStatus: HistoryChangeStatus? = null,
                   var version: String? = null,
                   var user: Any? = null,
                   var url: String? = null)
