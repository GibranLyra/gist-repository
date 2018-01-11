package gibran.com.br.githubservice.gists

import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import gibran.com.br.githubservice.GitHubApiModule
import gibran.com.br.githubservice.model.File
import gibran.com.br.githubservice.model.Gist
import io.reactivex.Observable
import timber.log.Timber


/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */

object GistsApi : GistsDataSource {
    private val gistsService: GistsService

    init {
        val retrofit = GitHubApiModule.retrofit
        gistsService = retrofit.create(GistsService::class.java)
    }

    override fun publicGists(page: Int, perPage: Int): Observable<List<Gist>> {
        return gistsService.publicGists(page, perPage)
                .flatMap {
                    val parsedGist: List<Gist> = parseFile(it)
                    return@flatMap Observable.just(parsedGist)
                }
                .doOnError { e -> Timber.e(e, "publicGists: %s", e.message) }
    }

    /* A extra job is required because file key is dynamic, we need to manually set the file property */
    private fun parseFile(it: JsonArray): List<Gist> {
        val gson = GitHubApiModule.getGsonBuilder()
        val parsedGist: List<Gist> = gson.fromJson(gson.toJson(it), object : TypeToken<List<Gist>>() {}.type)

        for (i in parsedGist.indices) {
            it[0].asJsonObject["files"]?.let { filesJson ->
                val entries = filesJson.asJsonObject.entrySet()
                for ((key) in entries) {
                    parsedGist[i].files?.file = gson.fromJson(filesJson.asJsonObject[key], File::class.java)
                }

            }
        }
        return parsedGist
    }

    override fun gist(id: String): Observable<Gist> {
        return gistsService.gist(id)
                .doOnError { e -> Timber.e(e, "gist: %s", e.message) }
    }
}
