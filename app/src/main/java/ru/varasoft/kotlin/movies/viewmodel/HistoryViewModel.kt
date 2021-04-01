package ru.varasoft.kotlin.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.varasoft.kotlin.movies.app.App.Companion.getHistoryDao
import ru.varasoft.kotlin.movies.app.AppState
import ru.varasoft.kotlin.movies.repository.LocalRepository
import ru.varasoft.kotlin.movies.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}
