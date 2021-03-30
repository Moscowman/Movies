package ru.varasoft.kotlin.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.kotlin.movies.app.App.Companion.getHistoryDao
import ru.varasoft.kotlin.movies.app.AppState
import ru.varasoft.kotlin.movies.model.MovieDTO
import ru.varasoft.kotlin.movies.repository.DetailsRepository
import ru.varasoft.kotlin.movies.repository.DetailsRepositoryImpl
import ru.varasoft.kotlin.movies.repository.LocalRepositoryImpl
import ru.varasoft.kotlin.movies.repository.RemoteMovieDataSource
import ru.varasoft.kotlin.movies.utils.convertDtoToModel

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(
        RemoteMovieDataSource()
    ),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getMovieFromRemoteSource(movie_id: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(movie_id, callBack)
    }

    fun saveMovieToDB(movie: MovieDTO) {
        historyRepositoryImpl.saveEntity(movie)
    }

    private val callBack = object :
        Callback<MovieDTO> {

        override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: MovieDTO): AppState {
            return if (serverResponse.id == null || serverResponse.title == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
            }
        }
    }
}