package com.app.babydogecloudminingapp.ui.gettingstarted

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.databinding.ActivityLoginBinding
import com.app.babydogecloudminingapp.ui.MainActivity
import com.app.babydogecloudminingapp.viewmodel.AuthViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.navigateUp.setOnClickListener {

            finish()
        }

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)

            startActivity(intent)
        }

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.emailCover.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.password.addTextChangedListener(object : TextWatcher {
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
            val password = binding.password.text.toString()

            if (email.trim().isEmpty()) {

                binding.emailCover.error = getString(R.string.email_required)

                return@setOnClickListener
            }

            if (password.trim().isEmpty()) {

                binding.passwordCover.error = getString(R.string.password_required)

                return@setOnClickListener
            }

            viewModel.doLoginAuth(email, password,this)
            binding.loadingBlack.visibility  = View.VISIBLE
            binding.customBar.visibility  = View.VISIBLE


        }

        viewModel.isSuccess().observe(this, Observer {

            val isSuccess = it

            if (isSuccess){

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }else{
                binding.loadingBlack.visibility  = View.GONE
                binding.customBar.visibility  = View.GONE
            }

        })


    }
}