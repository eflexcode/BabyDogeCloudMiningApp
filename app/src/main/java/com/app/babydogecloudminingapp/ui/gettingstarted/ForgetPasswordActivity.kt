package com.app.babydogecloudminingapp.ui.gettingstarted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.databinding.ActivityForgetPasswordBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.navigateUp.setOnClickListener {

            finish()

        }

        binding.email.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                binding.emailCover.isErrorEnabled = false

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.loginButton.setOnClickListener {

            val email = binding.email.text.toString()

            if (email.isNotEmpty()){

                Toast.makeText(this, "Verification email sent to $email",Toast.LENGTH_LONG).show()

            }else{
                binding.emailCover.error = getString(R.string.email_required)

            }

        }

    }
}