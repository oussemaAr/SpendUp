package app.elite.spendup.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tag_table")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id") val id: Int = 0,
    @ColumnInfo(name = "tag_name") val name: String,
    @ColumnInfo(name = "tag_thumbnail") val thumbnail: String,
) {
    override fun toString() = name
}