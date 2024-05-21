package com.sakthi.pdfscanner.presentation.home_screen

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.sakthi.pdfscanner.R
import com.sakthi.pdfscanner.presentation.home_screen.component.Pdfs
import com.sakthi.pdfscanner.presentation.ui.PDFScannerTheme
import java.io.File

@Composable
fun HomeScreen(
    scanner: GmsDocumentScanner,
    scannerLauncher: ActivityResultLauncher<IntentSenderRequest>,
    context: Context,
    viewModel: HomeViewModel
) {

    PDFScannerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            //Profile Header
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "profile image",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .background(
                            color = Color(0xFFBB9DDF)
                        )
                )

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .padding(end = 20.dp)
                ) {
                    Text(
                        text = "Hello User ",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Document Reader",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            //Info Banner
            Row(
                modifier = Modifier
                    .background(shape = RoundedCornerShape(20.dp), color = Color(0xFFC8EBFF))
                    .fillMaxWidth()
                    .fillMaxHeight(0.20f)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Scan Document",
                        fontSize = 20.sp,
                        color = Color.Black,
                    )
                    Text(
                        text = "From your Camera or Gallery",
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.pdf),
                    contentDescription = "file image",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                )


            }

            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Recent Documents",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                if (viewModel.recentPdfs.collectAsState().value.isEmpty()) {

                    Text(
                        text = "No Recent File Scanned",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier,
                        textAlign = TextAlign.Center

                    )
                }
                Row(modifier = Modifier.fillMaxSize()) {

                    val pdfs = viewModel.recentPdfs.collectAsState().value
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        items(pdfs) { pdf ->
                            Pdfs(pdf = File(context.filesDir, pdf.name), context, viewModel) { clickPdf ->

                                val file = File(
                                    context.filesDir,
                                    clickPdf.name
                                )
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    context.packageName + ".provider",
                                    file
                                )

                                Toast.makeText(context, "Opening PDF", Toast.LENGTH_SHORT).show()

                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    setDataAndType(uri, "application/pdf")
                                }

                                try {
                                    viewModel.updateRecentPdfs(context)
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    // Handle the case where no app is found to open PDF
                                    Toast.makeText(
                                        context,
                                        "No app found to open PDF",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }
                    }
                }

                ExtendedFloatingActionButton(
                    text = { Text(text = "Create PDF") },
                    icon = { Icon(Icons.Filled.Edit, contentDescription = "") },
                    onClick = {
                        scanner.getStartScanIntent(context as Activity)
                            .addOnSuccessListener { intentSender ->
                                scannerLauncher.launch(
                                    IntentSenderRequest.Builder(intentSender).build()
                                )
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .align(Alignment.BottomCenter)

                )


            }

        }
    }

}