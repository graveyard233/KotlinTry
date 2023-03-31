package com.example.kotlintry.roomTry

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "person_tb")
data class Person(
    @PrimaryKey
    var uid :Int ?= null,
    val name :String,
    val age :Int
//    @Embedded
//    val book :Book
) : Parcelable

@Entity(tableName = "book_tb",
foreignKeys = [ForeignKey(entity = Person::class,
    parentColumns = arrayOf("uid"),// 父类的主键
    childColumns = arrayOf("fatherId"),// 子类用来表示父类主键的字段，而且类型需要一致
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE// 一般写这个就可以
)])
data class Book(
    @PrimaryKey
    var bookId :Int ?= null,
    val bookName :String,
    val pages :Int,
    val fatherId :Int
)
