package com.example.bookandfriend.data.mappers


interface BookMapperResourceProvider {
    fun getUnknownAuthor(): String
    fun getNoDescription(): String
    fun getLanguageEnglish(): String
    fun getLanguageGerman(): String
    fun getLanguageRussian(): String
    fun getLanguageFrench(): String
    fun getLanguageNotAvailable(): String
}
