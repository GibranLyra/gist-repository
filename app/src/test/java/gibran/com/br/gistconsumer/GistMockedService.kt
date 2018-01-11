package gibran.com.br.gistconsumer

import com.google.gson.reflect.TypeToken
import gibran.com.br.githubservice.GitHubApiModule
import gibran.com.br.githubservice.gists.GistsDataSource
import gibran.com.br.githubservice.model.Gist
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */
object GistMockedService: GistsDataSource {
    internal val GISTS: List<Gist>

    init {
        val gson = GitHubApiModule.getGsonBuilder()
        val gistsRaw = javaClass.classLoader.getResourceAsStream("publicGistsMockedResponse.json")
        val gistsResponseJson = BufferedReader(InputStreamReader(gistsRaw))
        GISTS = gson.fromJson(gistsResponseJson, object : TypeToken<List<Gist>>() {}.type)
    }

    override fun publicGists(page: Int, perPage: Int): Observable<List<Gist>> {
        return Observable.just(GISTS)
    }

    override fun gist(id: String): Observable<Gist> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}