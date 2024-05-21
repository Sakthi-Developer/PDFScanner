package com.sakthi.pdfscanner.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.sakthi.pdfscanner.presentation.home_screen.HomeScreen
import com.sakthi.pdfscanner.presentation.home_screen.HomeViewModel
import com.sakthi.pdfscanner.presentation.home_screen.component.GetName
import com.sakthi.pdfscanner.presentation.ui.PDFScannerTheme
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            1
        )

        viewModel.updateRecentPdfs(this)

        val options = GmsDocumentScannerOptions.Builder()
            .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
            .setGalleryImportAllowed(true)
            .setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF,
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
            )
            .build()

        val scanner = GmsDocumentScanning.getClient(options)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {


            PDFScannerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->


                    val scannerLauncher =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = {
                                if (it.resultCode == RESULT_OK) {

                                    val result =
                                        GmsDocumentScanningResult.fromActivityResultIntent(it.data)

                                    result?.pdf?.let { pdf ->

                                        val file = File(
                                            filesDir,
                                            "${UUID.randomUUID().toString().substring(0, 12)}.pdf"
                                        )
                                        val fos = FileOutputStream(file)

                                        contentResolver.openInputStream(pdf.uri)?.use { pdf ->
                                            pdf.copyTo(fos)

                                        }

                                        Toast.makeText(
                                            applicationContext,
                                            "PDF saved",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        val fileUri = FileProvider.getUriForFile(
                                            this,
                                            application.packageName + ".provider",
                                            file
                                        )

                                        viewModel.updateRecentPdfs(this)


                                        Toast.makeText(
                                            applicationContext,
                                            "Pdf Created Successfully",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        Intent(Intent.ACTION_VIEW).apply {
                                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                            setDataAndType(fileUri, "application/pdf")
                                            startActivity(this)
                                        }

                                    }
                                }
                            })

                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        HomeScreen(
                            scanner,
                            scannerLauncher,
                            this@MainActivity,
                            viewModel
                        )
                    }
                }
            }
        }
    }
}
