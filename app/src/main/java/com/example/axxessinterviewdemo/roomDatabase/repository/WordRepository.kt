package com.example.axxessinterviewdemo.roomDatabase.repository

import androidx.lifecycle.LiveData
import com.example.axxessinterviewdemo.roomDatabase.dao.WordDao
import com.example.axxessinterviewdemo.roomDatabase.entity.Word

class WordRepository (private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()
    fun getAllByListCategoryId(listCategoryId: String): LiveData<List<Word>> {
        return wordDao.getAlphabetizedWordsById(listCategoryId)
    }
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}