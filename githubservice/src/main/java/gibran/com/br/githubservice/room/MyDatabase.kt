package gibran.com.br.githubservice.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import gibran.com.br.githubservice.model.Gist


/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */
@Database(entities = [(Gist::class)], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun gistDao(): GistDao
}