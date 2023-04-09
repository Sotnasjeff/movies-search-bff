package com.dataprovider.http.clients.openmoviedatabaseapi.v1

import com.core.v1.entity.Movie
import com.core.v1.repository.MovieSearchRepository
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.converters.MovieConverter.toDomain

class OpenMovieDatabaseApiSearchRepository(
    private val client: OpenMovieDatabaseApiHttpClient
) : MovieSearchRepository {
    override suspend fun getMoviesByTitle(title: String, year: Int, plot: String, type: String?): Movie? {
        return client.getMoviesByTitle(title, year, plot, type)?.toDomain()
    }
}