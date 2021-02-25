package app.elite.spendup.worker

import android.content.Context
import android.content.res.AssetManager.ACCESS_BUFFER
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.elite.spendup.data.local.AppDatabase
import app.elite.spendup.data.local.entities.TagEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class SeedDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val plantType = object : TypeToken<List<TagEntity>>() {}.type
        val jsonReader: JsonReader?

        return try {
            val inputStream = applicationContext.assets.open("tags.json", ACCESS_BUFFER)
            jsonReader = JsonReader(inputStream.reader())
            val plantList: List<TagEntity> = Gson().fromJson(jsonReader, plantType)
            val database = AppDatabase.getInstance(applicationContext)
            database.getTagDao().insertAll(plantList)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }

}