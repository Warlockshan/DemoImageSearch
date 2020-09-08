package com.example.axxessinterviewdemo.roomDatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.axxessinterviewdemo.roomDatabase.database.WordRoomDatabase
import com.example.axxessinterviewdemo.roomDatabase.entity.Word
import com.example.axxessinterviewdemo.roomDatabase.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)

        allWords = repository.allWords
    }

    fun getAllByListCategoryId(listCategoryId: String): LiveData<List<Word>> {
        return repository.getAllByListCategoryId(listCategoryId)
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}