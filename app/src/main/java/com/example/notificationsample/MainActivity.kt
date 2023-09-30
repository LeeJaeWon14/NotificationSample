package com.example.notificationsample

import android.Manifest
import android.app.NotificationManager
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
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

        // Notification action 통해 들어온 intent 처리
        intent?.getBooleanExtra("action", false)?.let {
            if(it)
                Toast.makeText(this, "Action from notification", Toast.LENGTH_SHORT).show()
        }

        checkPermission()

        var count : Int = 0
        binding.apply {
            notyButton.run {
                setOnClickListener {
                    NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "TEST", NotifyHelper.NOTIFY_TYPE.BASIC)
                }

                setOnLongClickListener {
                    NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "Big Picture Test", NotifyHelper.NOTIFY_TYPE.PICTURE)
                    false
                }
            }

            btnActionNotify.setOnClickListener {
                NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "ACTION BUTTON TEST", NotifyHelper.NOTIFY_TYPE.ACTION)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.getBooleanExtra("action", false)?.let {
            if(it)
                Toast.makeText(this, "Action from notification", Toast.LENGTH_SHORT).show()
        }
    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 9999)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissions.forEachIndexed { index, permission ->
            if(grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "권한이 없으면 앱을 실행할 수 없어요.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }

    }
}