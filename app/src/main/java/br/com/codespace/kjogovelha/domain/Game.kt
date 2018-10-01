package br.com.codespace.kjogovelha.domain

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.Toast
import br.com.codespace.kjogovelha.R

open class Game(val activity: Activity, val layoutId: Int, val btnRestartId: Int) {
    private val playOne = arrayListOf<Int>()
    private val playTwo = arrayListOf<Int>()
    private var currentPlay = 1

    private fun play(position: Int, btnSelected: Button) {
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
        val row1 = listOf(1, 2, 3)
        val row2 = listOf(4, 5, 6)
        val row3 = listOf(7, 8, 9)

        val col1 = listOf(1, 4, 7)
        val col2 = listOf(2, 5, 8)
        val col3 = listOf(3, 6, 9)

        val diag1 = listOf(1, 5, 9)
        val diag2 = listOf(3, 5, 7)

        var winner = -1

        with(playOne) {
            if (containsAll(row1) || containsAll(row2) || containsAll(row3) || containsAll(col1)
                    || containsAll(col2) || containsAll(col3)
                    || containsAll(diag1) || containsAll(diag2)) {
                winner = 1
            }
        }

        with(playTwo) {
            if (containsAll(row1) || containsAll(row2) || containsAll(row3) || containsAll(col1)
                    || containsAll(col2) || containsAll(col3)
                    || containsAll(diag1) || containsAll(diag2)) {
                winner = 2
            }
        }

        when(winner) {
            1 -> Toast.makeText(activity, "Parabéns! Jogador 1 Venceu", Toast.LENGTH_LONG).show()
            2 -> Toast.makeText(activity, "Parabéns! Jogador 2 Venceu", Toast.LENGTH_LONG).show()
        }

        if (winner > 0) {
            disableAllButtons()
        }
    }

    fun start()
    {
        for (i in 1 .. 9) {
            val id = activity.resources.getIdentifier("btn$i", "id", activity.packageName)
            val btn = activity.findViewById<Button>(id)
            btn.setOnClickListener { btnPosition(btn) }
        }

        activity.findViewById<Button>(btnRestartId).setOnClickListener{ restart() }
    }

    fun restart() {
        playOne.clear()
        playTwo.clear()
        activity.setContentView(layoutId)
        start()
    }

    private fun disableAllButtons() {
        for (i in 1 .. 9) {
            val id = activity.resources.getIdentifier("btn$i", "id", activity.packageName)
            val btn = activity.findViewById<Button>(id)
            btn.isClickable = false
        }
    }

}
