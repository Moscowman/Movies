package ru.varasoft.kotlin.movies.view.main

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import ru.varasoft.kotlin.movies.BuildConfig
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.databinding.FragmentMainBinding
import ru.varasoft.kotlin.movies.model.Movie
import ru.varasoft.kotlin.movies.model.MovieInListDTO
import ru.varasoft.kotlin.movies.view.details.DetailsFragment
import ru.varasoft.kotlin.movies.viewmodel.AppState
import ru.varasoft.kotlin.movies.viewmodel.MainViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val releasedMovieAdapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentReleasedMoviesRecyclerView.adapter = releasedMovieAdapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMoviesFromLocalSource()
        loadMoviesList()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val moviesData = appState.movieData
                releasedMovieAdapter.setMovies(moviesData)
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getMoviesFromLocalSource() })
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        releasedMovieAdapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMoviesList() {
        try {
            val uri =
                URL("https://api.tmdb.org/4/discover/movie?primary_release_year=2021&sort_by=vote_average.desc&page=1")
            val handler = Handler()
            Thread(Runnable {
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

                    val moviesListDTO: List<MovieInListDTO> =
                        Gson().fromJson(getLines(bufferedReader), Array<MovieInListDTO>::class.java).toList()
                    //handler.post { displayMovie(movieInListDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        val lines = reader.lines().collect(Collectors.joining("\n"))
        return lines
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}