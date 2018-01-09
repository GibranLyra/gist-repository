package gibran.com.br.githubservice


import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val API_VERSION: String = "v3/"

/**
 * Created by gibran.lyra on 23/08/2017.
 */
object GitHubApiModule {
    lateinit var retrofit: Retrofit private set

    fun setRetrofit(logLevel: LoggingInterceptor.Level = LoggingInterceptor.Level.BASIC) {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = LoggingInterceptor(Clock.systemDefaultZone(), logLevel)
        builder.addInterceptor(loggingInterceptor)

        val okClient = builder.build()
        retrofit = Retrofit.Builder()
                .baseUrl("https://developer.github.com/".plus(API_VERSION))
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
