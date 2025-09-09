package com.chikipay.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.chikipay.R

class BgPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.bg_preview)

        val iv = findViewById<ImageView>(R.id.bg)
        val label = findViewById<TextView>(R.id.label)

        // 4 backgrounds: main, cards, settings, transactions
        val items = arrayOf(
            R.drawable.background_main to "Main",
            R.drawable.background_cards to "Cards",
            R.drawable.background_settings to "Settings",
            R.drawable.background_transactions to "Transactions"
        )

        var i = 0
        fun showNext() {
            val (resId, name) = items[i % items.size]
            iv.setImageResource(resId)
            label.text = "$name  10s"
            i++
        }

        showNext()
        iv.postDelayed(object : Runnable {
            override fun run() {
                showNext()
                iv.postDelayed(this, 10_000L)
            }
        }, 10_000L)
    }
}