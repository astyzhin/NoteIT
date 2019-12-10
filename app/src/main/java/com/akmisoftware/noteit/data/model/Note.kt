package com.akmisoftware.noteit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int? = 0,
    @ColumnInfo
    var title: String? = "",
    @ColumnInfo
    var body: String? = ""
) {
}