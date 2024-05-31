package com.example.demo1


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MusicActivity : AppCompatActivity() {


    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var classicalPlayer: MediaPlayer
    private lateinit var studyMusicPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        supportActionBar?.hide()

        val playButton: Button = findViewById(R.id.calButtons)
        val classicalButton: Button = findViewById(R.id.timerBtn)
        val studyMusicButton: Button = findViewById(R.id.musicBtn)
        val homeBtn: ImageView = findViewById(R.id.homeBtn)


        // Initialize the MediaPlayer with the music files
        mediaPlayer = MediaPlayer.create(this, R.raw.whitenoise)
        classicalPlayer = MediaPlayer.create(this, R.raw.classicalmusic)
        studyMusicPlayer = MediaPlayer.create(this, R.raw.lofi)


        Log.d("MusicActivity", "Activity created and media players initialized")


        homeBtn.setOnClickListener {
            // Open other view; create intent
            val taskIntent = Intent(this, Home::class.java)
            // Start activity
            startActivity(taskIntent)
        }
        
        classicalButton.setOnClickListener {
            Log.d("MusicActivity", "Classical button clicked")
            if (classicalPlayer.isPlaying) {
                classicalPlayer.pause()
                classicalButton.text = "Classical Music"
                Log.d("MusicActivity", "Classical music paused")
            } else {
                stopAllPlayers()
                classicalPlayer.start()
                classicalButton.text = "Pause Music"
                Log.d("MusicActivity", "Classical music started")
            }
        }


        studyMusicButton.setOnClickListener {
            Log.d("MusicActivity", "Study music button clicked")
            if (studyMusicPlayer.isPlaying) {
                studyMusicPlayer.pause()
                studyMusicButton.text = "Study Music"
                Log.d("MusicActivity", "Study music paused")
            } else {
                stopAllPlayers()
                studyMusicPlayer.start()
                studyMusicButton.text = "Pause Music"
                Log.d("MusicActivity", "Study music started")
            }
        }


        playButton.setOnClickListener {
            Log.d("MusicActivity", "White noise button clicked")
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playButton.text = "White Noise"
                Log.d("MusicActivity", "White noise paused")
            } else {
                stopAllPlayers()
                mediaPlayer.start()
                playButton.text = "Pause Music"
                Log.d("MusicActivity", "White noise started")
            }
        }
    }


    private fun stopAllPlayers() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            Log.d("MusicActivity", "White noise stopped")
        }
        if (classicalPlayer.isPlaying) {
            classicalPlayer.pause()
            Log.d("MusicActivity", "Classical music stopped")
        }
        if (studyMusicPlayer.isPlaying) {
            studyMusicPlayer.pause()
            Log.d("MusicActivity", "Study music stopped")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resources
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
            Log.d("MusicActivity", "White noise media player released")
        }
        if (::classicalPlayer.isInitialized) {
            classicalPlayer.release()
            Log.d("MusicActivity", "Classical music media player released")
        }
        if (::studyMusicPlayer.isInitialized) {
            studyMusicPlayer.release()
            Log.d("MusicActivity", "Study music media player released")
        }
    }
}
