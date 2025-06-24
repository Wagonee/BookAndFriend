package com.example.bookandfriend.data.repository

import com.example.bookandfriend.data.database.dao.LibraryDao
import com.example.bookandfriend.data.database.entity.BookEntity
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LibraryRepositoryImpl(private val libraryDao: LibraryDao) : LibraryRepository {
    override suspend fun addBook(book: Book) {
        val repositoryBook = book.toEntity()
        libraryDao.addBook(repositoryBook)
    }

    override suspend fun deleteBook(bookId: String) {
        libraryDao.deleteBook(bookId)
    }

    override suspend fun getAllBook(): Flow<List<Book>> {
        return libraryDao.getAllBooks().map { entities ->
            entities.map { it.toDomain() }
        }
    }


    fun BookEntity.toDomain(): Book {
        return Book(
            id,
            title,
            author,
            coverId,
            publishYear,
            language,
            description,
            genres,
            isLiked = true
        )
    }

    fun Book.toEntity(): BookEntity {
        return BookEntity(id, title, author, coverId, publishYear, language, description, genres)
    }
}