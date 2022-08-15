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
import com.app.babydogecloudminingapp.databinding.ActivitySignUpBinding
import com.app.babydogecloudminingapp.ui.MainActivity
import com.app.babydogecloudminingapp.viewmodel.AuthViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.navigateUp.setOnClickListener {
            finish()
        }

        binding.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                binding.nameCover.isErrorEnabled = false

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
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

                binding.passwordCover.isErrorEnabled = false

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                binding.confirmPasswordCover.isErrorEnabled = false

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.loginButton.setOnClickListener {

            confirmAuthDetails()


        }

        viewModel.isSuccess().observe(this, Observer {

            val isSuccess = it

            if (isSuccess) {

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)

            } else {
                binding.loadingBlack.visibility = View.GONE
                binding.customBar.visibility = View.GONE
            }

        })


    }


    private fun confirmAuthDetails() {

        val fullName = binding.name.text.toString();
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString();


        if (fullName.trim().isEmpty()) {

            binding.nameCover.error = getString(R.string.full_name_required)


            return
        }
        if (email.trim().isEmpty()) {
            binding.emailCover.error = getString(R.string.email_required)


            return
        }
        if (password.trim().isEmpty()) {
            binding.passwordCover.error = getString(R.string.password_required)


            return
        }
        if (confirmPassword.trim().isEmpty()) {

            binding.confirmPasswordCover.error = getString(R.string.confirm_password_required)

            return
        }

        if (password.length < 6) {
            binding.passwordCover.error = getString(R.string.password_count_error)
            return

        }

        if (confirmPassword != password) {
            binding.passwordCover.error = getString(R.string.confirm_password)
            binding.confirmPasswordCover.error = getString(R.string.confirm_password)

            return
        }

        viewModel.doCreateAccountAuth(email, password, fullName, this)

        binding.loadingBlack.visibility = View.VISIBLE
        binding.customBar.visibility = View.VISIBLE
    }

}