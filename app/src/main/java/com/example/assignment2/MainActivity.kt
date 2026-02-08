package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment2.ui.theme.Assignment2Theme


class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(
                        studentName = "Welcome back, " + StudentInfo.NAME,
                        studentID = StudentInfo.ID,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun LandingPage(
    studentName: String,
    studentID: String,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
)
    { val idNumber = studentID.filter { it.isDigit() }

        // LocalContext.current gives the current Android Context
        // so we can call platform APIs
        val appContext = LocalContext.current
        val actionStartToSecondActivity = "com.example.assignment2.action.START_SECOND_ACTIVITY"

        PageColumn(
            modifier = modifier
        )
    {
        PageStyling(studentName)

        // This might just be overhead as the entry is a magic number
        if (idNumber.length == 7) {
            PageStyling(idNumber)
        } else {
            PageStyling("Invalid ID")
        }

        Button(onClick = {
            appContext.startActivity(
                Intent(appContext,
                    SecondActivity::class.java))
        })

        {
            Text("Start Second Activity Explicitly")
        }

        Button(
            onClick = {
                val implicitIntent = Intent(actionStartToSecondActivity)
                appContext.startActivity(implicitIntent)
            },
            modifier = Modifier
        ) {
            Text("Start Second Activity Implicitly")
        }

    }


}

@Preview(
    showBackground = true,
    showSystemUi = false
    )

@Composable
fun LandingPagePreview()
{
    Assignment2Theme {
        LandingPage(
            studentName = "Welcome back, Blessed",
            studentID = "7777777",
            innerPadding = PaddingValues(0.dp)
        )

    }
}