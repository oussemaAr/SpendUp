package app.elite.spendup.repository

import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TagEntity

class TagRepository(private val database: AppDatabase) {

    suspend fun getAllTags() = database.getTagDao().getTags()

    suspend fun getTagByName(name: String) = database.getTagDao().getTagByName(name)

    suspend fun addTag(tagEntity: TagEntity) = database.getTagDao().insert(tagEntity)

}