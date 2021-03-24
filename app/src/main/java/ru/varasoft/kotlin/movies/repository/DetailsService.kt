package ru.varasoft.kotlin.movies.repository

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.varasoft.kotlin.movies.BuildConfig
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.model.MovieDTO
import ru.varasoft.kotlin.movies.view.details.*
import ru.varasoft.kotlin.movies.viewmodel.AppState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val SERVER_ERROR = "Ошибка сервера"

class DetailsService(name: String = "DetailsService") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    private val callBack = object :
        Callback<MovieDTO> {

        override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
            val serverResponse: MovieDTO? = response.body()
            if (response.isSuccessful && serverResponse != null) {
                onSuccessResponse(serverResponse)
            } else {
                AppState.Error(Throwable(SERVER_ERROR))
            }
        }

        override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_LOAD_MOVIE -> {
                val movieId = intent.getIntExtra(MOVIE_EXTRA, -1)
                loadMovie(movieId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(movieId: Int) {
        val remoteMovieDataSource = RemoteMovieDataSource()
        remoteMovieDataSource.getMovieDetails(movieId, callBack)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieDTO: MovieDTO) {
        val fact = movieDTO.id
        if (fact == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieDTO)
        }
    }

    private fun onSuccessResponse(movieDTO: MovieDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(MOVIE_EXTRA, movieDTO)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}