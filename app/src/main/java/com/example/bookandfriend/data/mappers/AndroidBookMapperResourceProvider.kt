package com.example.bookandfriend.data.mappers

import android.content.Context
import com.example.bookandfriend.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidBookMapperResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : BookMapperResourceProvider {
    override fun getUnknownAuthor(): String = context.getString(R.string.unknown_author)
    override fun getNoDescription(): String = context.getString(R.string.no_description)
    override fun getLanguageEnglish(): String = context.getString(R.string.language_english)
    override fun getLanguageGerman(): String = context.getString(R.string.language_german)
    override fun getLanguageRussian(): String = context.getString(R.string.language_russian)
    override fun getLanguageFrench(): String = context.getString(R.string.language_french)
    override fun getLanguageNotAvailable(): String = context.getString(R.string.language_na)
}