package gibran.com.br.githubservice.gists

import gibran.com.br.githubservice.model.Gist
import io.reactivex.Observable

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */
interface GistsDataSource {
    fun publicGists(page: Int, perPage: Int): Observable<ArrayList<Gist>>

    fun gist(id: String): Observable<Gist>
}