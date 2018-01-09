package gibran.com.br.githubservice.gists

import gibran.com.br.githubservice.model.GistOwner
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val GISTS = "gists/"

/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */
internal interface GistsService {
    @GET(GISTS.plus("public"))
    fun publicGists(@Query("page") page: Int,
                    @Query("perPage") perPage: Int): Observable<List<GistOwner>>

    @GET(GISTS.plus("{id}"))
    fun gist(@Path("id") id: String): Observable<GistOwner>
}