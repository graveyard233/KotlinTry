package com.example.kotlintry.roomTry

import androidx.room.*

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: List<Person>)

    @Query("delete from person_tb")
    suspend fun clearPerson()

    @Query("select * from person_tb")
    suspend fun getAllPerson() :List<Person>

}

@Dao
interface BookDAO{
    @Upsert
    suspend fun upsertBook(book: List<Book>)

    @Query("delete from book_tb")
    suspend fun clearBook()
}