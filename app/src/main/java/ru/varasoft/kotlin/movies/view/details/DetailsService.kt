package ru.varasoft.kotlin.movies.view.details

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.varasoft.kotlin.movies.BuildConfig
import ru.varasoft.kotlin.movies.model.MovieInListDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private val ACTION_LOAD_MOVIES = "ru.varasoft.kotlin.movies.model.action.load_movies"
private val MOVIE_ID_EXTRA = "ru.varasoft.kotlin.movies.model.extra.MOVIE_ID"
val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"

class DetailsService(name: String = "DetailsService") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_LOAD_MOVIES -> {
                val movieId = intent.getIntExtra(MOVIE_ID_EXTRA, -1)
                loadMovie(movieId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(movieId: Int) {
        try {
            val uri =
                URL("https://api.tmdb.org/4/movie/${movieId}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty(
                    "Authorization",
                    "Bearer " + BuildConfig.THEMOVIEDB_API_KEY
                )
                urlConnection.setRequestProperty(
                    "Content-Type",
                    "application/json;charset=utf-8"
                )
                urlConnection.readTimeout = 10000
                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))

                val movieInListDTO: MovieInListDTO =
                    Gson().fromJson(getLines(bufferedReader), MovieInListDTO::class.java)
                onResponse(movieInListDTO)
            } catch (e: Exception) {
                Log.e("", "Fail connection", e)
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieInListDTO: MovieInListDTO) {
        val fact = movieInListDTO.id
        if (fact == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieInListDTO)
        }
    }

    private fun onSuccessResponse(movieInListDTO: MovieInListDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(MOVIE_ID_EXTRA, movieInListDTO.id)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    companion object {
        @JvmStatic
        fun startActionLoadMovies(context: Context, movieId: Int) {
            val intent = Intent(context, DetailsService::class.java).apply {
                action = ACTION_LOAD_MOVIES
                putExtra(MOVIE_ID_EXTRA, movieId)
            }
            context.startService(intent)
        }
    }
}