package com.example.notificationsample

import android.app.NotificationManager
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding.notyButton.run {
            setOnClickListener {
                NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "TEST", NotifyHelper.NOTIFY_TYPE.BASIC)
            }

            setOnLongClickListener {
                NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "Big Picture Test", NotifyHelper.NOTIFY_TYPE.PICTURE)
                false
            }
        }
    }
}