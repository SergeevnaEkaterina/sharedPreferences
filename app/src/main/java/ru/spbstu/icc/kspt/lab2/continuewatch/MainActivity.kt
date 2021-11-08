package ru.spbstu.icc.kspt.lab2.continuewatch
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var textSecondsElapsed: TextView
    var isOnScreen = true
    private lateinit var sharedPref: SharedPreferences
    var seconds: Int = 0

    var backgroundThread = Thread {
        while (true) {
            if (isOnScreen) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.seconds_left, seconds++)
                }
            }
        }
    }


    override fun onStart() {
        isOnScreen = true
        seconds = sharedPref.getInt(SECONDS_LEFT, seconds)
        super.onStart()
    }

    companion object { const val SECONDS_LEFT = "Seconds left" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsLeft)
        sharedPref = getSharedPreferences(SECONDS_LEFT, Context.MODE_PRIVATE)
        backgroundThread.start()
    }


    override fun onStop() {
        isOnScreen = false
        with(sharedPref.edit()) {
            putInt(SECONDS_LEFT, seconds)
            apply()
        }
        super.onStop()
    }



}
