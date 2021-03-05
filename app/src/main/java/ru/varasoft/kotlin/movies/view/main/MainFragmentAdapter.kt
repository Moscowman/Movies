package ru.varasoft.kotlin.movies.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.varasoft.kotlin.movies.R
import ru.varasoft.kotlin.movies.model.Movie

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

        private var movieData: List<Movie> = listOf()

        fun setWeather(data: List<Movie>) {
            movieData = data
            notifyDataSetChanged()
        }

        fun removeListener() {
            onItemViewClickListener = null
        }

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): MainViewHolder {
            return MainViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.fragment_main_recycler_item, parent, false) as View
            )
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(movieData[position])
        }

        override fun getItemCount(): Int {
            return movieData.size
        }

        inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(movie: Movie) {
                itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                        movie.russianName
                itemView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
            }
        }
}