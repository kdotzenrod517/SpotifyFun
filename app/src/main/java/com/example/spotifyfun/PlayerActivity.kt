package com.example.spotifyfun

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {

    lateinit var trackImageView : ImageView
    lateinit var playButton: ImageButton
    lateinit var pauseButton : ImageView
    lateinit var resumeButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupViews()
        setupListeners()
    }

    override fun onStop() {
        super.onStop()
        SpotifyService.disconnect()
    }

    private fun setupViews() {
        trackImageView = findViewById<ImageView>(R.id.trackImageView)
        SpotifyService.getCurrentTrackImage {
            trackImageView.setImageBitmap(it)
        }

        SpotifyService.playingState {
            when (it) {
                PlayingState.PLAYING -> showPauseButton()
                PlayingState.STOPPED -> showPlayButton()
                PlayingState.PAUSED -> showResumeButton()
            }
        }
    }

    private fun setupListeners() {
        playButton = findViewById<ImageButton>(R.id.playButton)
        pauseButton = findViewById<ImageButton>(R.id.pauseButton)
        resumeButton = findViewById<ImageButton>(R.id.resumeButton)

        playButton.setOnClickListener {
            SpotifyService.play("TBD")
            showPauseButton()
        }

        pauseButton.setOnClickListener {
            SpotifyService.pause()
            showResumeButton()
        }

        resumeButton.setOnClickListener {
            SpotifyService.resume()
            showPauseButton()
        }

        SpotifyService.suscribeToChanges {
            SpotifyService.getImage(it.imageUri) {
                trackImageView.setImageBitmap(it)
            }
        }
    }

    private fun showPlayButton() {
        playButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        resumeButton.visibility = View.GONE
    }

    private fun showPauseButton() {
        playButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE
        resumeButton.visibility = View.GONE
    }

    private fun showResumeButton() {
        playButton.visibility = View.GONE
        pauseButton.visibility = View.GONE
        resumeButton.visibility = View.VISIBLE
    }
}