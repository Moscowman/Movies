package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<MovieDTO> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: MovieDTO) {
        localDataSource.insert(convertWeatherToMovie(movie))
    }
}