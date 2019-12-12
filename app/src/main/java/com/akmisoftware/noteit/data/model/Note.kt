package com.akmisoftware.noteit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "note")
data class Note(
    @PrimaryKey
    @ColumnInfo
    var id: String = UUID.randomUUID().toString(),
    @ColumnInfo
    var title: String? = "",
    @ColumnInfo
    var body: String? = ""
) : Serializable