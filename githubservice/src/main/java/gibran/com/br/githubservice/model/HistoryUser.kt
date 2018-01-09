package gibran.com.br.githubservice.model

data class HistoryUser(var gistsUrl: String? = null,
                       var reposUrl: String? = null,
                       var followingUrl: String? = null,
                       var starredUrl: String? = null,
                       var login: String? = null,
                       var followersUrl: String? = null,
                       var type: String? = null,
                       var url: String? = null,
                       var subscriptionsUrl: String? = null,
                       var received_eventsUrl: String? = null,
                       var avatarUrl: String? = null,
                       var eventsUrl: String? = null,
                       var htmlUrl: String? = null,
                       var siteAdmin: Boolean = false,
                       var id: Int = 0,
                       var gravatarId: String? = null,
                       var organizationsUrl: String? = null)
