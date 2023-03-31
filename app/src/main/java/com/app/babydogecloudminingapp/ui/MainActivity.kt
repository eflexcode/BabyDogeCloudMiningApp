package com.app.babydogecloudminingapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import com.app.babydogecloudminingapp.MiningWorkManager
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.databinding.ActivityMainBinding
import com.app.babydogecloudminingapp.room.MiningCountDao
import com.app.babydogecloudminingapp.room.MiningCountDatabase
import com.app.babydogecloudminingapp.room.MiningViewModel
import com.app.babydogecloudminingapp.service.CountDownService
import com.app.babydogecloudminingapp.viewmodel.CountDownViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    private val TAG = "MainActivity"
    val countDownTime = 14400000
    private var mInterstitialAd: InterstitialAd? = null
    var isWithdrawButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView2.loadAd(adRequest)
        loadAd()

        var progress = 1000

        val mineWork: WorkRequest = OneTimeWorkRequestBuilder<MiningWorkManager>().build()

        val serviceContext = applicationContext;

        val countDownTimer: CountDownViewModel =
            ViewModelProvider(this).get(CountDownViewModel::class.java)
        var miningCountDao: MiningCountDao = MiningCountDatabase.getDB(this)?.miningDao()!!

        binding.withdraw.setOnClickListener {
            val  intent = Intent(this@MainActivity,WithdrawActivity::class.java)
            startActivity(intent)
        }

        binding.startButton.setOnClickListener {

            val miningHistory = miningCountDao.getEverything()

            val intent = Intent(this, CountDownService::class.java)

            if (miningHistory.isEmpty()) {
                intent.putExtra("isEmpty", true)
//                    Toast.makeText(this, "empty", Toast.LENGTH_LONG).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    serviceContext.startForegroundService(intent)

                }
            } else {
                intent.putExtra("isEmpty", false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    serviceContext.startForegroundService(intent)

                }

                mInterstitialAd?.show(this@MainActivity)

            }

        }

        val miningViewModel: MiningViewModel =
            ViewModelProvider(this)?.get(MiningViewModel::class.java)

        miningViewModel.getEverythingLive().observe(this) {

            if (it.isNotEmpty()) {

                binding.mineCount.text = it[0].balance.toString() + " BABYDOGE mined"

                val timePassed = it[0].timeLeft

                binding.customBar.progress =
                    ((countDownTime - timePassed) * 100 / countDownTime).toInt()
                val date = Date(timePassed)
                val formatter: DateFormat = SimpleDateFormat("HH:mm:ss")
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
                val dateFormatted: String = formatter.format(date)

                binding.countDownTimer.text = dateFormatted
            }
        }

    }

    fun loadAd() {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-8474139776659956/7899719200",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    loadAd()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.show(this@MainActivity)
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
//                isWithdrawButton = true
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
                loadAd()



            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                mInterstitialAd = null
                loadAd()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
//                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
//                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

    }
}