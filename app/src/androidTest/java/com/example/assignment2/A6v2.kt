import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class A6v2 {

    @Test
    fun a6TestSuite() {
        uiAutomator {
            startApp("com.example.assignment2")
            onElement { textAsString() == "Start Second Activity Explicitly" }.click()
            onElement { textAsString()?.contains("Data collection and security concerns") == true }
        }
    }
}