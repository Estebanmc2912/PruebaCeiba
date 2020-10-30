package com.example.pruebaceiba.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pruebaceiba.model.User

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM USERS WHERE id = :id")
    fun get(id: Int): User

    @Query("SELECT count(*) FROM USERS")
    fun getSizeUsers(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Update
    fun update(user : User)
}