package com.core.v1.repository

import com.core.v1.entity.Movie

interface MovieSearchRepository {
    suspend fun getMoviesByTitle(title: String, year: Int, plot: String, type: String?): Movie?
}