package gibran.com.br.githubservice.gists;

import java.util.Map;

import gibran.com.br.githubservice.model.Gist;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */

public interface GistsServiceJava {

    @GET("gists/public")
    Observable<Map<String, Object>> publicGists(@Query("page") int page,
                                                @Query("perPage") int perPage);

    @GET("gists/{id}")
    Observable<Gist> gist(@Path("id") String id);
}
