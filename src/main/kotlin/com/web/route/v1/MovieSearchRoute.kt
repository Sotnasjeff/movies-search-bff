package com.web.route.v1

import com.core.v1.service.MovieSearchService
import io.ktor.server.application.call
import io.ktor.server.locations.KtorExperimentalLocationsAPI
import io.ktor.server.locations.Location
import io.ktor.server.locations.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

@KtorExperimentalLocationsAPI
@ExperimentalCoroutinesApi
fun Routing.movieSearchRoute(kodein: Kodein) {
    val movieSearchService: MovieSearchService = kodein.direct.instance()
    get<MoviesSearchRequest> {
        try {
            call.respond(
                movieSearchService.getMoviesByTitle(
                    it.title,
                    it.year,
                    it.plot,
                    it.type
                )
            )
        } catch (e: Exception) {
            call.respond(e)
        }
    }
}

@KtorExperimentalLocationsAPI
@Location("/v1/movies/")
data class MoviesSearchRequest(
    val title: String,
    val year: Int,
    val plot: String,
    val type: String? = "",
)