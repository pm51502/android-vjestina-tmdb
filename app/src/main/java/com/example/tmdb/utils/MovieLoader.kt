package com.example.tmdb.utils

import com.example.tmdb.R
import com.example.tmdb.data.MovieItem

const val imagePath1 = R.drawable.iron_man_1
const val imagePath2 = R.drawable.lion_king_

data class MovieItemDetail(
    val id: Int = 0,
    val userScore: Float = 0F,
    val title: String = "",
    val year: Int = 0,
    val date: String = "",
    val country: String = "",
    val genres: List<String> = emptyList(),
    val duration: String = "",
    val imagePath: Int = 0,
    var isFavorite: Boolean = false,
    val overview: String = "",
    val crew: Map<String, List<String>> = emptyMap(),
    val topBilledCast: List<Cast> = emptyList(),
    //val imagePath: String
)

data class Cast(
    val name: String,
    val character: String,
    val imagePath: Int,
    //val imageUrl: String
)

object MovieLoader {
    var allMovies = listOf(
        MovieItem(
            id = 1,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 2,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        ),
        MovieItem(
            id = 3,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 4,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        ),
        MovieItem(
            id = 5,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 6,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        ),
        MovieItem(
            id = 7,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 8,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        ),
        MovieItem(
            id = 9,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 10,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        ),
        MovieItem(
            id = 11,
            title = "Iron man",
            overview = "",
            imagePath = imagePath1,
        ),
        MovieItem(
            id = 12,
            title = "Lion king",
            overview = "",
            imagePath = imagePath2,
        )
    )

    fun getMovieDetails(movieId: Int): MovieItemDetail {
        if (movieId % 2 != 0) {
            return MovieItemDetail(
                id = 1,
                userScore = 0.76f,
                title = "Iron man",
                year = 2008,
                date = "05/02/2008",
                country = "US",
                genres = listOf("Action", "Science Fiction", "Advanture"),
                duration = "2h 6m",
                imagePath = R.drawable.iron_man_1,
                isFavorite = false,
                overview = "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
                crew = mapOf(
                    "Director" to listOf("Jon Favreau"),
                    "Screenplay" to listOf(
                        "Mark Fergus",
                        "Hawk Ostby",
                        "Matt Holloway",
                        "Art Marcum"
                    )
                ),
                topBilledCast = listOf(
                    Cast(
                        name = "Robert Downey Jr.",
                        character = "Tony Stark / Iron Man",
                        imagePath = R.drawable.actor_1
                    ),
                    Cast(
                        name = "Terrence Howard",
                        character = "James Rhodes",
                        imagePath = R.drawable.actor_1
                    ),
                    Cast(
                        name = "Jeff Bridges",
                        character = "Obadiah Stane / Iron Monger",
                        imagePath = R.drawable.actor_1
                    )
                )
            )
        } else {
            return MovieItemDetail(
                id = 2,
                userScore = 0.71f,
                title = "Lion king",
                year = 2019,
                date = "07/21/2019",
                country = "HR",
                genres = listOf("Adventure", "Family", "Animation"),
                duration = "1h 58min",
                imagePath = R.drawable.lion_king_,
                isFavorite = false,
                overview = "Simba idolizes his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.",
                crew = mapOf(
                    "Director" to listOf("Jon Favreau"),
                    "Screenplay" to listOf("Jeff Nathanson"),
                    "Story" to listOf("Brenda Chapman"),
                    "Characters" to listOf("Linda Woolverton", "Jonathan Roberts", "Irene Mecchi")
                ),
                topBilledCast = listOf(
                    Cast(
                        name = "Chiwetel Ejiofor",
                        character = "Scar (voice)",
                        imagePath = R.drawable.actor_1
                    ),
                    Cast(
                        name = "John Oliver",
                        character = "Zazu (voice)",
                        imagePath = R.drawable.actor_1
                    ),
                    Cast(
                        name = "James Earl Jones",
                        character = "Mufasa (voice)",
                        imagePath = R.drawable.actor_1
                    )
                )
            )
        }
    }
}
