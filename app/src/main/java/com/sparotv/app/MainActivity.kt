package com.sparotv.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sampleMovies = listOf(
            MediaItemModel("Oppenheimer", "/storage/emulated/0/Movies/opp.mp4", "IMAX", true),
            MediaItemModel("Dune: Part Two", "/storage/emulated/0/Movies/dune2.mp4", "4K", true)
        )

        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                var playingVideoPath by remember { mutableStateOf<String?>(null) }

                if (playingVideoPath != null) {
                    VideoPlayerScreen(playingVideoPath!!) {
                        playingVideoPath = null
                    }
                } else {
                    HomeScreen(sampleMovies) { selectedPath ->
                        playingVideoPath = selectedPath
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(movies: List<MediaItemModel>, onPlay: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0E0E0E))) {
        TvLazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 40.dp, bottom = 40.dp)
        ) {
            item {
                Text(
                    text = "SPARO TV+",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 32.dp, bottom = 24.dp)
                )
            }
            item { MediaRow("Highest Quality (IMAX / 4K)", movies, onPlay) }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaRow(title: String, items: List<MediaItemModel>, onPlay: (String) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 32.dp, bottom = 12.dp)
        )
        TvLazyRow(contentPadding = PaddingValues(start = 32.dp, end = 32.dp)) {
            items(items.size) { index ->
                val item = items[index]
                Surface(
                    onClick = { onPlay(item.filePath) },
                    colors = ClickableSurfaceDefaults.colors(
                        containerColor = Color(0xFF1A1A1A),
                        focusedContainerColor = Color.White
                    ),
                    modifier = Modifier.width(220.dp).height(130.dp).padding(end = 16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(item.title, fontWeight = FontWeight.Bold)
                            Text(item.quality, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
