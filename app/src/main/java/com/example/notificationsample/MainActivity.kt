package com.example.notificationsample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.notificationsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel" //channel ID 생성
    private val NOTIFICATION_ID = 0 //Notification ID 생성
    private var notificationManager : NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //자동 생성된 Binding Class의 인스턴스 생성
        val binding = ActivityMainBinding.inflate(layoutInflater)
        //ViewBinding 연결
        setContentView(binding.root)

        var count : Int = 0
        binding.notyButton.setOnClickListener {
            //count ++
            notificationCreate()
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //SDK 버전 확인
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID, "Test Notification",
                                                            NotificationManager.IMPORTANCE_HIGH)
            with(notificationChannel) {
                enableLights(true)
                lightColor = getColor(R.color.colorPrimary)
                enableVibration(true) //진동 설정
                description = "Notification Setting"
                notificationManager!!.createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun getNotificationBuilder() : NotificationCompat.Builder {
        //Notification 클릭 했을 때 Intent 시작
        val notificationIntent = Intent(this, MainActivity::class.java)
        //Notification을 감싸고 전달해주는 PendingIntent 정의
        val notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Builder 생성
        val notifiyBuilder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .apply {
                setContentTitle("Notification Title")
                setContentText("Notification Text")
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentIntent(notificationPendingIntent) //Builder에 Intent 적용
                setAutoCancel(true) //Notification 클릭하면 자동 삭제제
           }

        return notifiyBuilder
    }

    private fun notificationCreate() {
        //Builder 생성
        val notifyBuilder = getNotificationBuilder()
        //Manager를 통해 notification 전달
        notificationManager!!.notify(NOTIFICATION_ID, notifyBuilder.build())
    }
}