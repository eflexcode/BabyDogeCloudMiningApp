package com.app.babydogecloudminingapp.ui.gettingstarted

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.adapter.WelcomeSlideAdapter
import com.app.babydogecloudminingapp.databinding.ActivityWelcomeBinding
import com.app.babydogecloudminingapp.utill.Util
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    private val TAG = "WelcomeActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Util.CHANNEL_NAME,
                Util.CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager =  getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)

        }

        val imageList = ArrayList<Int>(4)
        imageList.add(R.drawable.baby_doge_logo4)
        imageList.add(R.drawable.baby_doge_logo1)
        imageList.add(R.drawable.baby_doge_logo_2)
        imageList.add(R.drawable.baby_doge_logo5)

        val adapter = WelcomeSlideAdapter(imageList)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.indicator, binding.pager) { tab, position -> }.attach()
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.logIf.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
        binding.getStarted.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }

//        val consentObject = JSONObject()
//        try {
//            // Provide correct consent value to sdk which is obtained by User
//            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true)
//            // Provide 0 if GDPR is not applicable and 1 if applicable
//            consentObject.put("gdpr", "1")
//            // Provide user consent in IAB format
//            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_IAB, “ << consent in IAB format >> ”)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        InMobiSdk.init(this, "Insert InMobi Account ID here", consentObject,  SdkInitializationListener() {
//            @Override
//            fun onInitializationComplete(error : Error?) {
//                if (null != error) {
//                    Log.e(TAG, "InMobi Init failed -" + error.getMessage())
//                } else {
//                    Log.d(TAG, "InMobi Init Successful")
//                }
//            }
//        })

    }


}