package com.app.babydogecloudminingapp.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.room.MiningCount
import com.app.babydogecloudminingapp.room.MiningCountDao
import com.app.babydogecloudminingapp.room.MiningCountDatabase
import com.app.babydogecloudminingapp.room.MiningRepository
import com.app.babydogecloudminingapp.ui.MainActivity
import com.app.babydogecloudminingapp.utill.Util

class CountDownService : Service() {

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        val isEmpty = intent.getBooleanExtra("isEmpty", false)

        val miningRepository: MiningRepository = MiningRepository(this)

        if (isEmpty) {

            val miningCount = MiningCount(1, 1000000, 0, 0)

            miningRepository.insert(miningCount)

        } else {
//            Toast.makeText(this, "Not Empty", Toast.LENGTH_LONG).show()
        }
        val actionIntent = Intent(this, MainActivity::class.java)
        actionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val actionPendingIntent =
            PendingIntent.getActivity(this, 233, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE )

        val notification = NotificationCompat.Builder(this, Util.CHANNEL_NAME)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setContentIntent(actionPendingIntent)
            .setUsesChronometer(true)
            .setColor(resources.getColor(R.color.light_blue))
            .setSmallIcon(R.drawable.ic_paw)
            .build()

        val notificationDone = NotificationCompat.Builder(this, Util.CHANNEL_NAME)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.mining_done))
            .setContentIntent(actionPendingIntent)
            .setColor(resources.getColor(R.color.light_blue))
            .setSmallIcon(R.drawable.ic_paw)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)

        startForeground(23, notification)

        var miningCountDao: MiningCountDao = MiningCountDatabase.getDB(this)?.miningDao()!!
//        14400000
        val timer = object : CountDownTimer(14400000, 1000) {
            override fun onTick(timePassed: Long) {

                val miningHistory = miningCountDao.getEverything()

                val newBalance = miningHistory[0].balance + 50

                val miningCount = MiningCount(1, newBalance, 50, timePassed)

                miningRepository.update(miningCount)

            }

            override fun onFinish() {

                notificationManagerCompat.notify(29, notificationDone)

                stopForeground(true)

                stopSelf()

            }
        }
        timer.start()



        return START_NOT_STICKY
    }

}