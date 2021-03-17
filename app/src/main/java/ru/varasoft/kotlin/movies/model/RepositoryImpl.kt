package ru.varasoft.kotlin.movies.model

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.varasoft.kotlin.movies.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class RepositoryImpl : Repository {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMoviesFromServer(): List<MovieInListDTO> = loadMoviesList()

    override fun getMoviesFromLocalStorage(): List<MovieInListDTO> = listOf()

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMoviesList() :List<MovieInListDTO> {
        var moviesList: MutableList<MovieInListDTO> = mutableListOf()
        try {
            val handler = Handler()
            Thread({
                var totalPages = 1
                var page = 1
                while (page <= totalPages) {
                    val uri =
                        URL("https://api.tmdb.org/4/discover/movie?primary_release_year=2021&sort_by=vote_average.desc&page=$page")
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

                        val moviesListPageDTO: MoviesListPage =
                            Gson().fromJson(getLines(bufferedReader), MoviesListPage::class.java)

                        for (movie: MovieInListDTO in moviesListPageDTO.results) {
                            moviesList.add(movie)
                        }
                        totalPages = moviesListPageDTO.total_pages
                        page++
                    } catch (e: Exception) {
                        Log.e("", "Fail connection", e)
                        e.printStackTrace()
                        break
                    } finally {
                        urlConnection.disconnect()
                    }
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
        return moviesList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        val lines = reader.lines().collect(Collectors.joining("\n"))
        return lines
    }

}
