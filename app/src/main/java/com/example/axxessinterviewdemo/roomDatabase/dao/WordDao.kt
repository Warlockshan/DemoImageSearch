package com.example.axxessinterviewdemo.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.axxessinterviewdemo.roomDatabase.entity.Word

@Dao
interface WordDao {
    @Query("SELECT * from commentsimage_table ORDER BY imageId ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Query("SELECT * from commentsimage_table WHERE imageId = :imageId")
    fun getAlphabetizedWordsById(imageId: String): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM commentsimage_table")
    suspend fun deleteAll()
}