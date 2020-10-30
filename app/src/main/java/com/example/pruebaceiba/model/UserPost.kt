package com.example.pruebaceiba.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="UserPosts")
data class UserPost (
        @ColumnInfo(name = "userId")
        val userId : Int,
        @PrimaryKey
        val id : Int,
        @ColumnInfo(name = "title")
        val title : String ,
        @ColumnInfo(name = "body")
        val body : String) {
}