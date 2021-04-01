package ru.varasoft.kotlin.movies.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.databinding.FragmentMainReleasedMovieRecycleItemBinding
import ru.varasoft.kotlin.movies.model.MovieDTO

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.ReleasedMovieViewHolder>() {

    private var recycleType: Int = TYPE_RELEASED_MOVIE
    private var movieData: List<MovieDTO> = listOf()

    fun setRecycleType(type: Int) {
        recycleType = type
    }

    fun setMovies(data: List<MovieDTO>) {
        movieData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReleasedMovieViewHolder {
        return when (viewType) {
            TYPE_RELEASED_MOVIE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_main_released_movie_recycle_item, parent, false)
                ReleasedMovieViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ReleasedMovieViewHolder, position: Int) {
        val element = movieData[position]
        when (holder) {
            is ReleasedMovieViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    override fun getItemViewType(position: Int): Int {
        return recycleType
    }

    inner class ReleasedMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val _binding: FragmentMainReleasedMovieRecycleItemBinding =
            FragmentMainReleasedMovieRecycleItemBinding.bind(itemView)
        private val binding get() = _binding

        fun bind(item: MovieDTO) {
            with(binding) {
                movieNameTextView.text = item.title
                ratingTextView.text = item.vote_average.toString()
                yearOfReleaseTextView.text = item.release_date
                posterImageView.setImageResource(R.drawable.abstract_poster)
                ratingImageView.setImageResource(R.drawable.star)
            }
            Glide.with(itemView)
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${item.poster_path}")
                .apply(RequestOptions().override(300, 450))
                .into(binding.posterImageView);
            itemView.setOnClickListener { onItemViewClickListener?.onItemViewClick(item) }
        }
    }

    companion object {
        private const val TYPE_RELEASED_MOVIE = 0
        private const val TYPE_UPCOMING_MOVIE = 1
    }
}