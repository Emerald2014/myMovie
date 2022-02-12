package ru.kudesnik.mymovie.model.entities.rest.rest_entities

data class MovieKP(
    val id: Int,
    val name: String,
    val genres: List<GenresKP>,
    val rating: RatingKP,
    val year: Int,
    val persons: List<PersonsKP>,
    val poster: PosterKP,
    val movieLength: Int
)

data class GenresKP(val name: String)
data class RatingKP(val kp: Float)
data class PersonsKP(
    val id: Int,
    val enProfession: String = "director",
    val name: String,
    val birthPlace: List<BirthPlace>,
    val birthday: String
)

data class PosterKP(val url: String)
data class BirthPlace(val value: String)