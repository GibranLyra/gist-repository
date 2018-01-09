package gibran.com.br.githubservice.model

/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */
data class Gist(var url:String? = null,
                var forksUrl:String? = null,
                var commitsUrl:String? = null,
                val id:String,
                var gitPullUrl:String? = null,
                var gitPushUrl:String? = null,
                var htmUrl:String? = null,
                var files: Files? = null,
                var isPublic: Boolean = false,
                var createdAt :String? = null,
                var updatedAt :String? = null,
                var description :String? = null,
                var comments :String? = null,
                var user :Any? = null,
                var commentsUrl :String? = null,
                var owner: Owner? = null,
                var forks :List<Fork?>? = null,
                var history :List<History?>? = null,
                var truncated :Boolean = false)