package com.example.assignment2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.assignment2.ui.theme.Assignment2Theme
import android.content.IntentFilter



class MainActivity : ComponentActivity()
{
    private val myReceiver = MyBroadcastReceiver()
    private var receiverRegistered = false

    private val MY_BASIC_BROADCAST_ACTION = "com.example.assignment2.MY_BASIC_BROADCAST_ACTION"

    override fun onStart() {
        super.onStart()

        if (!receiverRegistered) {
            val filter = IntentFilter(MY_BASIC_BROADCAST_ACTION)
            registerReceiver(myReceiver, filter, RECEIVER_NOT_EXPORTED)
            receiverRegistered = true
        }
    }

    override fun onStop() {
        super.onStop()

        if (receiverRegistered) {
            unregisterReceiver(myReceiver)
            receiverRegistered = false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(
                        studentName = "Welcome back, " + StudentInfo.NAME,
                        studentID = StudentInfo.ID,
                        innerPadding = innerPadding,
                        myBasicBroadcastAction = MY_BASIC_BROADCAST_ACTION
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
    myBasicBroadcastAction: String,
    modifier: Modifier = Modifier
)
    { val idNumber = studentID.filter { it.isDigit() }

        val appContext = LocalContext.current
        val actionStartToSecondActivity = "com.example.assignment2.action.START_SECOND_ACTIVITY"

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val serviceIntent = Intent(appContext, MyForegroundService::class.java)
                ContextCompat.startForegroundService(appContext, serviceIntent)
            }
        }

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

        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        appContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val serviceIntent = Intent(appContext, MyForegroundService::class.java)
                    ContextCompat.startForegroundService(appContext, serviceIntent)
                } else {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            },
            modifier = Modifier
        ) {
            Text("Start Service")
        }

        Button(onClick = {
            val intent = Intent(myBasicBroadcastAction).setPackage(appContext.packageName)
            appContext.sendBroadcast(intent)
        }) {
            Text("Send Broadcast")
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
            myBasicBroadcastAction = "com.example.assignment2.MY_BASIC_BROADCAST_ACTION",
            innerPadding = PaddingValues(0.dp)
        )

    }
}