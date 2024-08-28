package com.example.colorapp.design


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.colorapp.ColorViewModel
import com.example.colorapp.data.ColorData
import com.example.colorapp.data.ColorRepository
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ColorScreen(
    viewModel: ColorViewModel = hiltViewModel()
) {
    val colors = viewModel.colorList.collectAsState(initial = emptyList())
    val unSyncedCount = remember{ mutableStateOf(0) }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF7E5EF3)).padding(10.dp)
            ) {
                Text(
                    text = "Color App",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(top = 30.dp,start = 5.dp)

                )
                Spacer(modifier  = Modifier.width(120.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF9379F0)
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                ) {
                    SyncButton(viewModel, unSyncedCount.value)

                }


            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addColor() },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp)).size(60.dp).padding()
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }

        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            ColorList(colors.value)
        }
    }
}

@Composable
fun ColorList(colorData: List<ColorData>) {
    LazyColumn {
        items(colorData.size / 2) { index ->
            val firstItemIndex = index * 2
            val secondItemIndex = index * 2 + 1
            ColorRow(
                colorData = listOf(
                    colorData.getOrNull(firstItemIndex),
                    colorData.getOrNull(secondItemIndex),
                )
            )
        }
        // Handle the last row (if there are any remaining items)
        if (colorData.size % 2 != 0) { // If there's an odd number of items
            items(1) {
                ColorRow(
                    colorData = listOfNotNull(
                        colorData.getOrNull(colorData.size - 1)
                    )
                )
            }
        }
    }
}

@Composable
fun ColorRow(
    colorData: List<ColorData?>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        colorData.forEach { item ->
            if (item != null) {
                ColorItem(colorData = item)
            }

        }

    }

}

@Composable
fun ColorItem(colorData: ColorData) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = dateFormatter.format(colorData.date)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(colorData.color)).copy(alpha = 0.7f)
        ),
        modifier = Modifier
            .size(height = 120.dp, width = 170.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {

        Text(
            text = colorData.color.toUpperCase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier
                .padding(start = 6.dp, top = 9.dp)
                .align(Alignment.Start)
        )
        Divider(
            color = Color.White,
            modifier = Modifier
                .padding(start = 8.dp)
                .height(1.dp)
                .width(90.dp)
                .align(Alignment.Start)
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.End)
                .padding(top = 25.dp, end = 8.dp),
        ) {
            Text(
                text = "Created at",
                fontSize = 13.sp,
                color = Color.White,

                )
            Text(
                text = formattedDate,
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier

            )

        }


    }
}

@Composable
fun SyncButton(viewModel: ColorViewModel, unsyncedCount: Int) {
    IconButton(onClick = { viewModel.syncColors() }) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Sync")
        if (unsyncedCount > 0) {
            Text(
                text = unsyncedCount.toString(),
                color = Color.Red,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorScreenPreview() {
    ColorScreen()
}