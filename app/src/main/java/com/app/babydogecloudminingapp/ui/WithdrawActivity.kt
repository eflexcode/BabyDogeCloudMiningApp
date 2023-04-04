package com.app.babydogecloudminingapp.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.databinding.ActivityWithdrawBinding
import com.app.babydogecloudminingapp.room.MiningCountDao
import com.app.babydogecloudminingapp.room.MiningCountDatabase
import com.app.babydogecloudminingapp.room.MiningViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.startapp.sdk.adsbase.Ad
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.adlisteners.AdEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WithdrawActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null

    lateinit var binding: ActivityWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val startAppAd = StartAppAd(this)


        val miningViewModel: MiningViewModel =
            ViewModelProvider(this)?.get(MiningViewModel::class.java)

//        miningViewModel.getEverythingLive().observe(this) {
//
//            if (it.isNotEmpty()) {
//
//
//            }
//        }

        val miningCountDao: MiningCountDao = MiningCountDatabase.getDB(this)?.miningDao()!!

        binding.withdrawButton.setOnClickListener {

            val miningHistory = miningCountDao.getEverything()

            if (miningHistory.isNotEmpty()) {

                if (miningHistory[0].balance < 5734408573.17) {

                    var materialAlertDialogBuilder =
                        MaterialAlertDialogBuilder(this@WithdrawActivity)
                    materialAlertDialogBuilder.setIcon(R.drawable.apology)
                    materialAlertDialogBuilder.setTitle(getString(R.string.dialog_title))
                    materialAlertDialogBuilder.setMessage(getString(R.string.dialog_message))
                    materialAlertDialogBuilder.setPositiveButton(
                        getString(R.string.dialog_button_text),
                        object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.dismiss()
                                finish()
                            }

                        })
                    startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO,
                        object : AdEventListener {
                            override fun onReceiveAd(p0: Ad) {
                                startAppAd.showAd()
                            }

                            override fun onFailedToReceiveAd(p0: Ad?) {
                                TODO("Not yet implemented")
                            }

                        })


                }
            }else{
                val materialAlertDialogBuilder =
                    MaterialAlertDialogBuilder(this@WithdrawActivity)
                materialAlertDialogBuilder.setIcon(R.drawable.apology)
                materialAlertDialogBuilder.setTitle(getString(R.string.dialog_title))
                materialAlertDialogBuilder.setMessage(getString(R.string.dialog_message))
                materialAlertDialogBuilder.setPositiveButton(
                    getString(R.string.dialog_button_text),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.dismiss()
                            finish()
                        }

                    })
                startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO,
                    object : AdEventListener {
                        override fun onReceiveAd(p0: Ad) {
                            startAppAd.showAd()
                        }

                        override fun onFailedToReceiveAd(p0: Ad?) {
                            TODO("Not yet implemented")
                        }

                    })



            }

        }

//        loadAd()

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
                    mInterstitialAd?.show(this@WithdrawActivity)
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