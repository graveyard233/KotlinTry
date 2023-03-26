package com.example.kotlintry.roomTry

import kotlin.random.Random

object DataGenerator {
    fun generatePerson() :List<Person>{
        val personList = mutableListOf<Person>()
        for (i in 0..15){
            personList.add(Person(uid = i, name = "person-$i", age = (0..80).random()))
        }
        return personList
    }

    fun generateBook() : List<Book>{
        val bookList  = mutableListOf<Book>()
        for (i in 10..15){
            bookList.add(Book(bookId = i, bookName = "book_$i", pages = (100..1000).random(), fatherId = i))
        }
        return bookList
    }

}