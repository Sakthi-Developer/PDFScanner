package com.sakthi.pdfscanner.presentation.home_screen.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakthi.pdfscanner.R
import com.sakthi.pdfscanner.presentation.home_screen.HomeViewModel
import java.io.File


@Composable
fun Pdfs(pdf: File, context: Context, viewModel: HomeViewModel, onClick: (File) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(shape = RoundedCornerShape(20.dp), color = Color(0xFFF2F2F2))
            .clickable { onClick(pdf) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.pdf),
                contentDescription = "file image",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.15f)
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.90f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                //File name
                Text(
                    text = pdf.name,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                //File size
                Text(
                    text = "Size : ${pdf.length() / 1024} Kb",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                )
            }

            FileOperations(
                modifier = Modifier.align(Alignment.CenterVertically),
                pdf,
                context,
                viewModel
            )

        }
    }
}

