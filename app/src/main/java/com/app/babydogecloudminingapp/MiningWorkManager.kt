package com.app.babydogecloudminingapp

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class MiningWorkManager(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val TAG = "MiningWorkManager"
    override fun doWork(): Result {

        var result: Result

        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, "onFinish: oooooooooooooooooooooooooooo")

            }

            override fun onFinish() {

//                Toast.makeText(context,"ooooo",Toast.LENGTH_LONG).show()

                Log.d(TAG, "onFinish: oooooooooooooooooooooooooooo")

            }
        }
        timer.start()
        return Result.success()


    }
}