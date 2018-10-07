package br.com.codespace.kjogovelha

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import br.com.codespace.kjogovelha.helper.WindowHelper

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        Runnable { WindowHelper.hideBar(this.window) }.run()
        val img = findViewById<ImageView>(R.id.imgSplashLogo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        img.startAnimation(anim)
        MediaPlayer.create(this, R.raw.welcome).start()

        Handler().postDelayed({
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
