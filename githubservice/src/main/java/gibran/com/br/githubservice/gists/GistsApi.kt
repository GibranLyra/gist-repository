package gibran.com.br.githubservice.gists

import com.google.gson.JsonArray
import com.google.gson.JsonElement
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

    override fun publicGists(page: Int, perPage: Int): Observable<ArrayList<Gist>> {
        return gistsService.publicGists(page, perPage)
                .flatMap {
                    val parsedGist: ArrayList<Gist> = parseFile(it)
                    return@flatMap Observable.just(parsedGist)
                }
                .doOnError { e -> Timber.e(e, "publicGists: %s", e.message) }
    }

    override fun gist(id: String): Observable<Gist> {
        return gistsService.gist(id)
                .map {
                    val gson = GitHubApiModule.getGsonBuilder()
                    val parsedGist: Gist = gson.fromJson(gson.toJson(it), Gist::class.java)
                    parseGist(it.asJsonObject["files"], parsedGist)
                    return@map parsedGist
                }
                .doOnError { e -> Timber.e(e, "gist: %s", e.message) }
    }

    /* A extra job is required because file key is dynamic, we need to manually set the file property */
    private fun parseFile(it: JsonArray): ArrayList<Gist> {
        val gson = GitHubApiModule.getGsonBuilder()
        val parsedGist: ArrayList<Gist> = gson.fromJson(gson.toJson(it), object : TypeToken<ArrayList<Gist>>() {}.type)
        for (i in parsedGist.indices) {
            it[i].asJsonObject["files"]?.let { filesJson ->
                parseGist(filesJson, parsedGist[i])
            }
        }
        return parsedGist
    }

    /* A extra job is required because file key is dynamic, we need to manually set the file property */
    private fun parseGist(jsonElement: JsonElement, gist: Gist) {
        val gson = GitHubApiModule.getGsonBuilder()
        val entries = jsonElement.asJsonObject.entrySet()
        for ((key) in entries) {
            gist.files?.file = gson.fromJson(jsonElement.asJsonObject[key], File::class.java)
        }
    }
}
