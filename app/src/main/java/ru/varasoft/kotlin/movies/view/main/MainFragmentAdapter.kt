package ru.varasoft.kotlin.movies.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.databinding.FragmentMainReleasedMovieRecycleItemBinding
import ru.varasoft.kotlin.movies.model.MovieInListDTO
import java.util.*

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    private var recycleType: Int = TYPE_RELEASED_MOVIE
    private var movieData: List<MovieInListDTO> = listOf()

    fun setRecycleType(type: Int) {
        recycleType = type
    }

    fun setMovies(data: List<MovieInListDTO>) {
        movieData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_RELEASED_MOVIE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_main_released_movie_recycle_item, parent, false)
                ReleasedMovieViewHolder(view)
            }
            TYPE_UPCOMING_MOVIE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_main_upcoming_movie_recycle_item, parent, false)
                UpcomingMovieViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = movieData[position]
        when (holder) {
            is ReleasedMovieViewHolder -> holder.bind(element)
            is UpcomingMovieViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    override fun getItemViewType(position: Int): Int {
        return recycleType
    }

    inner class ReleasedMovieViewHolder(itemView: View) : BaseViewHolder<MovieInListDTO>(itemView) {
        private val _binding: FragmentMainReleasedMovieRecycleItemBinding =
            FragmentMainReleasedMovieRecycleItemBinding.bind(itemView)
        private val binding get() = _binding

        override fun bind(item: MovieInListDTO) {
            with (binding) {
                movieNameTextView.text = item.title
                ratingTextView.text = item.vote_average.toString()
                yearOfReleaseTextView.text = item.release_date
                posterImageView.setImageResource(R.drawable.abstract_poster)
                ratingImageView.setImageResource(R.drawable.star)
            }
        }
    }

    inner class UpcomingMovieViewHolder(itemView: View) : BaseViewHolder<MovieInListDTO>(itemView) {

        override fun bind(item: MovieInListDTO) {
            //Do your view assignment here from the data model
        }
    }

    companion object {
        private const val TYPE_RELEASED_MOVIE = 0
        private const val TYPE_UPCOMING_MOVIE = 1
    }
}