package ru.varasoft.kotlin.movies.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.varasoft.kotlin.movies.databinding.FragmentDetailsBinding
import ru.varasoft.kotlin.movies.model.MovieInListDTO

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: MovieInListDTO

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<MovieInListDTO>(BUNDLE_EXTRA)
        if (movie != null) binding.apply {

        }
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment = DetailsFragment().apply { arguments = bundle }
    }
}