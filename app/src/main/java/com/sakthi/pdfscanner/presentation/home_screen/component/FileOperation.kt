package com.sakthi.pdfscanner.presentation.home_screen.component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.sakthi.pdfscanner.presentation.home_screen.HomeViewModel
import java.io.File

@Composable
fun FileOperations(
    modifier: Modifier = Modifier,
    file: File,
    context: Context,
    viewModel: HomeViewModel
) {

    var expandedMenu by remember { mutableStateOf(false) }
    var makeDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    IconButton( // Use IconButton for a three-dot icon
        onClick = { expandedMenu = !expandedMenu },
        modifier = modifier
            .clickable(onClick = { expandedMenu = !expandedMenu })
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = "More options")
    }
    DropdownMenu(
        modifier = modifier
            .background(Color(0xFFF2F2F2))
            .padding(8.dp),
        expanded = expandedMenu, onDismissRequest = { expandedMenu = false },
        offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp, y = 8.dp)

    ) {

        DropdownMenuItem(text = {
            Row {
                Icon(Icons.Default.Edit, contentDescription = "Rename")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Rename")
            }
        }, onClick = {
            makeDialog = true
        })

        DropdownMenuItem(text = {
            Row {
                Icon(Icons.Default.Share, contentDescription = "Share")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Share")
            }
        }, onClick = {

            expandedMenu = false
            Toast.makeText(context, "Sharing File..", Toast.LENGTH_SHORT).show()
            Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(
                    Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(context, context.packageName + ".provider", file)
                )
                context.startActivity(Intent.createChooser(this, "Share via"))
            }

        })
        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFC70000)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete", color = Color(0xFFC70000))
                }
            },
            onClick = {
                file.delete()
                expandedMenu = false
                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show()
                viewModel.updateRecentPdfs(context)
            })

    }

    if (makeDialog) {

        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            file.renameTo(File(file.parent, "${text.trim()}.pdf"))
                            text = ""
                            expandedMenu = false
                            viewModel.updateRecentPdfs(context)
                        }
                        makeDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        makeDialog = false
                        expandedMenu = false
                    }) {
                    Text("Dismiss")
                }
            },
            title = { Text(text = "Rename") },

            text = {
                OutlinedTextField(
                    value = text,
                    label = { Text(text = "Enter File Name") },
                    onValueChange = {
                        text = it
                    },
                )
            }
        )
    }

}

