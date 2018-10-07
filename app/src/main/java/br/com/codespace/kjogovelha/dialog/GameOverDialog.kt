package br.com.codespace.kjogovelha.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.codespace.kjogovelha.R
import br.com.codespace.kjogovelha.helper.WindowHelper
import br.com.codespace.kjogovelha.domain.Game

class GameOverDialog(context: Context?, private val game: Game) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.game_over)
        val image = findViewById<ImageView>(R.id.imgGameOver)
        val msg = findViewById<TextView>(R.id.txtGameOver)
        val btnRestart = findViewById<Button>(R.id.btnGameRestart)
        val btnRevenge = findViewById<Button>(R.id.btnGameRevenge)

        WindowHelper.hideBar(this.window)

        image.setImageResource(R.drawable.winner)
        if (game.isDraw()) {
            image.setImageResource(R.drawable.angry_grandmother)
            msg.text = context.getString(R.string.draw_message)
        } else {
            msg.text = context.getString(R.string.winner_message, game.winner)
        }

        btnRestart.setOnClickListener {
            game.restart()
            dismiss()
        }

        btnRevenge.setOnClickListener {
            game.revenge()
            dismiss()
        }

        super.onCreate(savedInstanceState)
    }
}