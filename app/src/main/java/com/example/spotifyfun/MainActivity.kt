package com.example.spotifyfun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectButton = findViewById<Button>(R.id.connectButton)

        connectButton.setOnClickListener {
            SpotifyService.connect(this) {
                if(it) {
                    showPlayer()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SpotifyService.disconnect()
    }

    private fun showPlayer() {
        val intent = Intent(this, PlayerActivity::class.java)
        startActivity(intent)
    }
}