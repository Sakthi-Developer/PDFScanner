package com.sakthi.pdfscanner.presentation.home_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel : ViewModel() {

    private val _recentPdfs = MutableStateFlow<List<File>>(emptyList())
    val recentPdfs: StateFlow<List<File>> = _recentPdfs.asStateFlow()

    fun updateRecentPdfs(context: Context) {

        viewModelScope.launch {
            val results = try {
                val pdfs = context.filesDir.listFiles { file -> file.extension == "pdf" }?.toList()
                    ?: emptyList()
                pdfs.map { File(it.path) }.reversed()// Use map directly, no need for nested lambdas
            } catch (e: Exception) {
                // Log or handle the exception more gracefully (e.g., show a toast)
                Log.e(e.message, "Error while updating recent PDFs")
                emptyList()
            }
            _recentPdfs.value = results
        }
    }

}