package gibran.com.br.githubservice.gists

import gibran.com.br.githubservice.GitHubApiModule
import gibran.com.br.githubservice.model.Gist
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */

object GistsApi: GistsDataSource {
    private val gistsService: GistsService

    init {
        val retrofit = GitHubApiModule.retrofit
        gistsService = retrofit.create(GistsService::class.java)
    }

    override fun publicGists(page: Int, perPage: Int): Observable<List<Gist>> {
        return gistsService.publicGists(page, perPage)
                .doOnError { e -> Timber.e(e, "publicGists: %s", e.message) }
    }

    override fun gist(id: String): Observable<Gist> {
        return gistsService.gist(id)
                .doOnError { e -> Timber.e(e, "gist: %s", e.message) }
    }
}
