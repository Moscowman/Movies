package ru.varasoft.kotlin.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.varasoft.kotlin.movies.model.Repository
import ru.varasoft.kotlin.movies.model.RepositoryImpl

class MainViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMoviesFromLocalSource() = getDataFromLocalSource()

    fun getMoviesFromRemoteSource() = getDataFromRemoteSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMoviesFromLocalStorage()))
        }.start()
    }
    private fun getDataFromRemoteSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            val moviesList = repositoryImpl.getMoviesFromServer()
            liveDataToObserve.postValue(AppState.Success(moviesList))
        }.start()
    }
}