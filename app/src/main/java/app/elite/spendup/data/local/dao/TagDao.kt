package app.elite.spendup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.elite.spendup.data.local.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert
    suspend fun insert(tagEntity: TagEntity)

    @Insert
    suspend fun insertAll(tagEntity: List<TagEntity>)

    @Query("SELECT * FROM tag_table ORDER BY tag_name")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tag_table WHERE tag_name = :name LIMIT 1")
    suspend fun getTagByName(name: String): TagEntity
}