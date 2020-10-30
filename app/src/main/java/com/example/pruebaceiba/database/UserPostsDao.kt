package com.example.pruebaceiba.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pruebaceiba.model.UserPost

@Dao
interface UserPostsDao {


    @Query("SELECT * FROM UserPosts")
    fun getAll(): LiveData<List<UserPost>>

    @Query("SELECT * FROM UserPosts WHERE id = :id")
    fun get(id: Int): UserPost

    @Query("SELECT count(*) FROM UserPosts")
    fun getSizeUsersPosts(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg usersPosts: UserPost)

    @Update
    fun update(usersPost: UserPost)

}




