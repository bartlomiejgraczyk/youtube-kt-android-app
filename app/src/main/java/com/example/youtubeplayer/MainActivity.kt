package com.example.youtubeplayer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
                     YouTubePlayer.PlaybackEventListener, View.OnClickListener {

    companion object {
        const val API_KEY: String = "AIzaSyDj5nBzha-2kjyQZrwH5Zr0iCHW95a4-Nk"
        private val VIDEOS_IDs: List<String> = listOf("Tm8LGxTLtQk", "pyi0ZfuIIvo", "RRKJiM9Njr8")
    }

    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var playButton: FloatingActionButton
    private lateinit var stopButton: FloatingActionButton
    private lateinit var previousBtn: FloatingActionButton
    private lateinit var nextBtn: FloatingActionButton
    private var currentVideoIndex: Int = 0

    private lateinit var player: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPlayerView()
        setupButtons()
    }

    private fun setupPlayerView() {
        youTubePlayerView = findViewById(R.id.ytView)
        youTubePlayerView.initialize(API_KEY, this)
    }

    private fun setupButtons() {
        playButton = findViewById(R.id.playBtn)
        stopButton = findViewById(R.id.stopBtn)
        previousBtn = findViewById(R.id.previousVideoBtn)
        nextBtn = findViewById(R.id.nextVideoBtn)

        playButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
        previousBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
    }

    private fun setupPlayer(_youTubePlayer: YouTubePlayer) {
        player = _youTubePlayer
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
        player.setPlaybackEventListener(this)
        loadVideo(currentVideoIndex)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
        if (!wasRestored) {
            setupPlayer(youTubePlayer)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
            Toast.makeText(this, "Initialization failed!", Toast.LENGTH_LONG).show()
    }

    override fun onClick(view: View?) {
        when (view) {
            playButton -> {
                if (player.isPlaying) {
                    pauseVideo()
                } else {
                    playVideo()
                }
            }
            stopButton -> stopVideo()
            previousBtn -> previousVideo()
            nextBtn -> nextVideo()
        }
    }

    private fun playVideo() {
        player.play()
    }

    private fun pauseVideo() {
        if (player.isPlaying) {
            player.pause()
        }
    }

    private fun stopVideo() {
        loadVideo(currentVideoIndex)
        player.pause()
    }

    private fun nextVideo() {
        if (currentVideoIndex < VIDEOS_IDs.size - 1) {
            currentVideoIndex++
        } else {
            currentVideoIndex = 0
        }

        loadVideo(currentVideoIndex)
    }

    private fun previousVideo() {
        if (currentVideoIndex > 0) {
            currentVideoIndex--
        } else {
            currentVideoIndex = VIDEOS_IDs.size - 1
        }

        loadVideo(currentVideoIndex)
    }

    private fun loadVideo(index: Int) {
        player.loadVideo(VIDEOS_IDs[index])
    }

    override fun onPlaying() {
        playButton.setImageResource(R.drawable.ic_baseline_pause_24)
    }

    override fun onPaused() {
        playButton.setImageResource(R.drawable.ic_baseline_play_arrow_36)
    }

    override fun onStopped() {
        playButton.setImageResource(R.drawable.ic_baseline_play_arrow_36)
    }

    override fun onBuffering(p0: Boolean) {
    }

    override fun onSeekTo(miliseconds: Int) {
    }
}