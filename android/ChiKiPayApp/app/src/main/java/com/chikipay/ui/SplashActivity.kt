package com.chikipay.ui
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chikipay.R
class SplashActivity: AppCompatActivity(){
  override fun onCreate(b:Bundle?){ super.onCreate(b); setContentView(R.layout.activity_splash)
    val logo=findViewById<ImageView>(R.id.logo)
    logo.setImageResource(R.drawable.splash_logo_986)
    Handler(Looper.getMainLooper()).postDelayed({ logo.setImageResource(R.drawable.splash_logo_chikipay) },1200)
    Handler(Looper.getMainLooper()).postDelayed({ startActivity(Intent(this, MainActivity::class.java)); finish() },2200)
  }
}



