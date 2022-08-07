package com.example.lab6

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Long = 0,
    @ColumnInfo(name = "user_name")
    var name: String,
    var login: String,
    var password: String
)
