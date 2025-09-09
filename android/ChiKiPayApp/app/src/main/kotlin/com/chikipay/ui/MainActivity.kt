package com.chikipay.ui
import androidx.core.view.WindowCompat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chikipay.R
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}



