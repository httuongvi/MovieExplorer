package com.tuongvi.movieexplorer

import android.util.Log
fun grade(score: Int): String {
    Log.d("Grade", "score = $score")

    return when (score){
        10 -> "A"
        8, 9 -> "B"
        6, 7 -> "C"
        5 -> "D"
        in 0..4 -> "F"
        else -> "Score must be in [0,10]"
    }
}

fun safeLength(s: String?): Int = s?.length ?: 0


fun fizzbuzz(){
//    for (i in 1..30){
//        if (i % 3 == 0 && i % 5 == 0){
//            println("FizzBuzz when i = $i")
//        }
//        else if (i % 3 == 0){
//            println ("Fizz when i = $i")
//        }
//        else if (i % 5 == 0) {
//            println("Buzz when i = $i")
//        }
//
//    }
    for (i in 1..30) {
        when {
            i % 3 == 0 && i % 5 == 0 -> Log.d("FizzBuzz", "FizzBuzz when i = $i" )
            i % 3 == 0 ->  Log.d("FizzBuzz", "Fizz when i = $i" )
            i % 5 == 0 -> Log.d("FizzBuzz", "Buzz when i = $i" )
        }
    }
}
