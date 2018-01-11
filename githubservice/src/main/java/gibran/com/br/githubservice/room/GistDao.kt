package gibran.com.br.githubservice.room

import android.arch.persistence.room.*
import gibran.com.br.githubservice.model.Gist

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */
@Dao
interface GistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gist: Gist): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(gists: List<Gist>): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(gist: Gist): Int

    @Delete
    fun delete(gist: Gist): Int

    @Query("SELECT * FROM gist")
    fun getAll(): List<Gist>
}