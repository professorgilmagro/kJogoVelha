package br.com.codespace.kjogovelha.domain

import android.app.Activity
import android.media.MediaPlayer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import br.com.codespace.kjogovelha.R
import br.com.codespace.kjogovelha.dialog.GameOverDialog
import br.com.codespace.kjogovelha.helper.WindowHelper

open class Game(val activity: Activity, val layoutId: Int) {
    private val playOne = arrayListOf<Int>()
    private val playTwo = arrayListOf<Int>()
    private var currentPlay = 1
    private val soundClick = MediaPlayer.create(activity, R.raw.u_click)
    private val soundClickWin = MediaPlayer.create(activity, R.raw.click_mjgh_lab)
    var winner = -1
    var score1 = 0
    var score2 = 0

    private fun play(position: Int, btnSelected: Button) {
        val anim = AnimationUtils.loadAnimation(activity, R.anim.button_animation)
        btnSelected.startAnimation(anim)
        with(btnSelected) {
            when (currentPlay) {
                1 -> {
                    text = "X"
                    setBackgroundResource(R.color.colorPlayer1)
                    playOne.add(position)
                    currentPlay = 2
                }
                2 -> {
                    text = "O"
                    setBackgroundResource(R.color.colorPlayer2)
                    playTwo.add(position)
                    currentPlay = 1
                }
            }

            checkResult()
            isClickable = false

            if (winner == -1) soundClick.start() else soundClickWin.start()
        }
    }

    private fun btnPosition(view: View) = when (view.id) {
        R.id.btn1 -> play(1, view as Button)
        R.id.btn2 -> play(2, view as Button)
        R.id.btn3 -> play(3, view as Button)
        R.id.btn4 -> play(4, view as Button)
        R.id.btn5 -> play(5, view as Button)
        R.id.btn6 -> play(6, view as Button)
        R.id.btn7 -> play(7, view as Button)
        R.id.btn8 -> play(8, view as Button)
        R.id.btn9 -> play(9, view as Button)
        else -> play(0, view as Button)
    }

    private fun checkResult() {
        val possibleResults = listOf(
                listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9), listOf(1, 4, 7),
                listOf(2, 5, 8), listOf(3, 6, 9), listOf(1, 5, 9), listOf(3, 5, 7)
        )

        possibleResults.forEach {
            if (playOne.containsAll(it)) {
                winner = 1
                score1 ++
                return@forEach
            }
        }

        possibleResults.forEach {
            if (playTwo.containsAll(it)) {
                winner = 2
                score2 ++
                return@forEach
            }
        }

        if (isGameOver()) {
            disableAllButtons()
            updateScore()
            val sound = if (isDraw()) R.raw.loser else R.raw.winner
            GameOverDialog(activity, this).show()
            MediaPlayer.create(activity, sound).start()
        }
    }

    fun start()
    {
        updateScore()
        for (i in 1 .. 9) {
            val id = activity.resources.getIdentifier("btn$i", "id", activity.packageName)
            val btn = activity.findViewById<Button>(id)
            btn.setOnClickListener { btnPosition(btn) }
            btn.isClickable = true
        }

        Runnable { WindowHelper.hideBar(activity.window) }.run()
    }

    fun restart() {
        revenge()
        score2 = 0
        score1 = 0
    }

    fun revenge() {
        winner = -1
        playOne.clear()
        playTwo.clear()
        activity.setContentView(layoutId)
        soundClickWin.setOnCompletionListener { start() }
        soundClickWin.start()
    }

    private fun disableAllButtons() {
        for (i in 1 .. 9) {
            val id = activity.resources.getIdentifier("btn$i", "id", activity.packageName)
            val btn = activity.findViewById<Button>(id)
            btn.isClickable = false
        }
    }

    fun isDraw(): Boolean {
        return (playOne + playTwo).size == 9 && winner == -1
    }

    fun isGameOver(): Boolean {
        return isDraw() || winner > 0
    }

    fun updateScore() {
        activity.findViewById<TextView>(R.id.txtScorePlayOne).text = score1.toString()
        activity.findViewById<TextView>(R.id.txtScorePlayTwo).text = score2.toString()
    }

}
