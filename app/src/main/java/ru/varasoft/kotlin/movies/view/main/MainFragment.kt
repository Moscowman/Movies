package ru.varasoft.kotlin.movies.view.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.varasoft.kotlin.movies.ConnectivityListener
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.databinding.FragmentMainBinding
import ru.varasoft.kotlin.movies.model.MovieInListDTO
import ru.varasoft.kotlin.movies.model.RepositoryImpl
import ru.varasoft.kotlin.movies.view.details.DetailsFragment
import ru.varasoft.kotlin.movies.viewmodel.AppState
import ru.varasoft.kotlin.movies.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val repository = RepositoryImpl()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val releasedMovieAdapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieInListDTO) {
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
        viewModel.getMoviesFromRemoteSource()

        val connectivityListener = ConnectivityListener(activity!!)
        connectivityListener.getLiveData().observe(
            viewLifecycleOwner,
            Observer {
                Toast.makeText(
                    activity,
                    if (it) "Сеть доступна" else "Сеть недоступна",
                    Toast.LENGTH_SHORT
                ).show()
            })


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
        fun onItemViewClick(movie: MovieInListDTO)
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}
