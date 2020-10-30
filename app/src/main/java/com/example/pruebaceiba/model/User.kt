package com.example.pruebaceiba.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Users")
data class User (
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "phone")
    val phone : String,
    @ColumnInfo(name = "email")
    val email : String) {
}

