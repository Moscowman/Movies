package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteMovieDataSource) :
    DetailsRepository {

    override fun getMovieDetailsFromServer(
        id: Int,
        callback: retrofit2.Callback<MovieDTO>
    ) {
        remoteDataSource.getMovieDetails(id, callback)
    }
}
