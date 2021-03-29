package ru.varasoft.kotlin.movies.repository

import ru.varasoft.kotlin.movies.model.MovieDTO
import ru.varasoft.kotlin.movies.room.HistoryDao
import ru.varasoft.kotlin.movies.room.HistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<MovieDTO> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: MovieDTO) {
        localDataSource.insert(convertMovieToEntity(movie))
    }

    fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<MovieDTO> {
        return entityList.map {
            MovieDTO(
                it.movie_id,
                arrayOf(),
                null,
                it.original_title,
                "",
                null,
                null,
                null,
                it.title,
                false
            )
        }
    }

    fun convertMovieToEntity(movie: MovieDTO): HistoryEntity {
        return HistoryEntity(0, movie.id?:0, movie.original_title, movie.title)
    }
}