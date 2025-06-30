package com.example.bookandfriend.data.repository

import com.example.bookandfriend.data.database.dao.LibraryDao
import com.example.bookandfriend.data.database.entity.BookEntity
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(private val libraryDao: LibraryDao) :
    LibraryRepository {
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
            id = id,
            title = title,
            author = author,
            coverId = coverId,
            publishYear = publishYear,
            language = language,
            description = description,
            genres = genres,
            isLiked = true
        )
    }

    fun Book.toEntity(): BookEntity {
        return BookEntity(id = id, title = title, author = author, coverId = coverId, publishYear = publishYear, language = language, description = description, genres = genres)
    }
}