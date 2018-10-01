package br.com.codespace.kjogovelha

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.codespace.kjogovelha.domain.Game

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Game(this, R.layout.activity_main, R.id.btn_restart).start()
    }
}
