package com.app.babydogecloudminingapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.databinding.ActivitySplashBinding
import com.app.babydogecloudminingapp.ui.gettingstarted.WelcomeActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    lateinit var mIntent: Intent

    lateinit var binding : ActivitySplashBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        Handler().postDelayed({

            if (FirebaseAuth.getInstance().currentUser == null) {

                mIntent = Intent(this, WelcomeActivity::class.java)

            } else {
                mIntent = Intent(this, MainActivity::class.java)
            }

            startActivity(mIntent)
            finish()
        },6000)



    }
}