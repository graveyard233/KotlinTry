package com.example.kotlintry.roomTry

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "person_tb")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val uid :Int,
    val name :String,
    val age :Int,
    val book :Book
)

@Entity(tableName = "book_tb",
foreignKeys = [ForeignKey(entity = Person::class,
parentColumns = arrayOf("mid"), childColumns = arrayOf("fatherId"),
    onUpdate = ForeignKey.CASCADE
)])
data class Book(
    val bookId :Int,
    val bookName :String,
    val pages :Int,
    val fatherId :Int
)
