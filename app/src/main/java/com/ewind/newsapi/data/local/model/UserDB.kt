package com.ewind.newsapi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(
    @ColumnInfo(name = "name")
    var name: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 1
}