package com.example.assignment2

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.assignment2.ui.theme.Assignment2Theme

class SecondActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                SecondActivityPage()
            }
        }
    }
}


@Composable
fun SecondActivityPage(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity

    val challenges = listOf(
        "Device fragmentation and OS compatibility across the Android ecosystem.",
        "Resource management due to constraints such as battery and memory.",
        "Deciding between native, web or cross-platform apps.",
        "Data collection and security concerns.",
        "Keeping up with upstream Android updates."
    )

    PageColumn(
        modifier = modifier)
    {
        PageStyling("Five Mobile Software Engineering Challenges")

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(challenges) {index, items ->
                PageStyling("${index + 1}. $items")
            }
        }

        Button(
            onClick = {activity.finish()},
            modifier = Modifier
        ) {
            Text("Main Activity")
        }
    }
}


