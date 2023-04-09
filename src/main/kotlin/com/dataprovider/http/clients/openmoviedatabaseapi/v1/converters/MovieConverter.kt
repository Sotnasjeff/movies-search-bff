package com.dataprovider.http.clients.openmoviedatabaseapi.v1.converters

import com.core.v1.entity.Movie as DomainMovie
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.entity.OpenMovieDatabaseResponse

object MovieConverter {
    fun OpenMovieDatabaseResponse.toDomain(): DomainMovie {
        return DomainMovie(
            title = this.Title,
            year = this.Year,
            actors = this.Actors,
            boxOffice = this.BoxOffice,
            country = this.Country,
            director = this.Director,
            genre = this.Genre,
            language = this.Language,
            plot = this.Plot,
            awards = this.Awards,
            production = this.Production,
            metascore = this.Metascore,
            poster = this.Poster
        )
    }
}