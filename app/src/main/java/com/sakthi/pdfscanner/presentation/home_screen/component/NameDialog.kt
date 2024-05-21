package com.sakthi.pdfscanner.presentation.home_screen.component

import android.opengl.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun GetName(modifier: Modifier = Modifier, visibility: Boolean) {

    /*var isVisible = remember { mutableStateOf(false) }
    isVisible = visibility*/

    if (visibility) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }
        , title = { Text(text = "Testing")})
    }
}