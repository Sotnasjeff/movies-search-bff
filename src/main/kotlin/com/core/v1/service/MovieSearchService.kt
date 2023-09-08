package com.core.v1.service

import com.core.v1.entity.Movie
import com.core.v1.repository.MovieSearchRepository
import com.exception.MovieNotFoundException
import com.metric.MetricService

class MovieSearchService(
    private val movieSearchRepository: MovieSearchRepository,
    private val metricService: MetricService
) {
    suspend fun getMoviesByTitle(title: String, year: Int, plot: String, type: String?): Movie {
        return movieSearchRepository.getMoviesByTitle(title, year, plot, type) ?: throw MovieNotFoundException()
    }
}