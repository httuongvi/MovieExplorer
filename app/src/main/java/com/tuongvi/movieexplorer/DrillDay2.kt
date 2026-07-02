package com.tuongvi.movieexplorer

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
fun main(){
    val students = listOf(
        Student("s1", 5.5),
        Student("s2", 7.0),
        Student("s3", 8.5),
        Student("s4", 8.0),
        Student("s1", 5.0)
    )

    val studentMaxScore = students.maxByOrNull { it.score }?.name
    println(studentMaxScore)

    students.filter { it.score >= 8.0 }.forEach{println(it)}

    val averageScore = students.map{it.score}.average()
    println(averageScore)

    println(getDescription(Genre.ACTION))

    testLoadFakeData()

}


data class Student (
    val name: String,
    val score: Double
)

enum class Genre {
    ACTION,
    COMEDY,
    DRAMA,
    HORROR
}

fun getDescription(genre: Genre): String{
    when(genre){
        Genre.ACTION -> return "Action is..."
        Genre.COMEDY -> return "Comedy is..."
        Genre.DRAMA -> return "Drama is..."
        Genre.HORROR -> return "Horror..."
    }
}

//fun getDescription1(genre: Genre): String =
//    when(genre){
//        Genre.ACTION -> return "Action"
//        Genre.COMEDY -> return "Comedy"
//        Genre.DRAMA -> return "Dam"
//        Genre.HORROR -> return "Horror"
//    }

suspend fun loadFakeData(): List<String> {
    delay(1000)
    return listOf("Mov1", "Mov2", "Mov3")
}

fun testLoadFakeData() = runBlocking {
    println("Bắt đầu tải danh sách phim")

    val movies = loadFakeData()

    println("Đã tải xong")
    movies.forEach { println(it) }


}
