package com.example.bookandfriend.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.bookandfriend.data.database.entity.BookEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface LibraryDao {
    @Insert(onConflict = REPLACE)
    suspend fun addBook(book: BookEntity)

    @Query("DELETE FROM library WHERE id = :bookId")
    suspend fun deleteBook(bookId: String)

    @Query("SELECT * FROM library")
    fun getAllBooks(): Flow<List<BookEntity>>
}