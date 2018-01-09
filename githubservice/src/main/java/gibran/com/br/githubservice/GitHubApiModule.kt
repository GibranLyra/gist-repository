package gibran.com.br.githubservice


import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal const val BASE_URL: String = "https://api.github.com/"

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
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
