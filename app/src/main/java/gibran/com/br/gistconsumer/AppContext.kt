package gibran.com.br.gistconsumer

import android.app.Application



import android.arch.persistence.room.Room
import com.jakewharton.threetenabp.AndroidThreeTen
import gibran.com.br.githubservice.GitHubApiModule
import gibran.com.br.githubservice.room.MyDatabase
import timber.log.Timber
/**
 * Created by gibranlyra on 09/01/18 for gist_consumer.
 */
class AppContext : Application() {
    internal lateinit var database: MyDatabase

    companion object {
        lateinit var instance: AppContext private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeTimezone()
        initializeTimber()
        initializeApiModules()
        initializeRoom()
    }

    private fun initializeTimezone() {
        AndroidThreeTen.init(this)
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeApiModules() {
        //Initialize ApiModule Singleton
        GitHubApiModule.setRetrofit()
    }

    private fun initializeRoom() {
        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "MyDataBase").build()
    }
}
