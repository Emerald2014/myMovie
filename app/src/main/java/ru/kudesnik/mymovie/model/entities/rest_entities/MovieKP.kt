package ru.kudesnik.mymovie.model.entities.rest_entities

data class MovieKP(
    val id: Int,
    val name: String,
    val genres: List<GenresKP>,
    val rating: RatingKP,
    val year: Int,
    val persons: List<PersonsKP>

)

data class GenresKP(val name: String)
data class RatingKP(val kp: Float)
data class PersonsKP(val enProfession: String = "director", val name: String)