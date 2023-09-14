package com.example.myandroiddemo

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity

private const val TAG = "MainActivity_New"

class MainActivity_New : AppCompatActivity() {
    var switch: Switch? = null
    var switch_Sys: Switch? = null
    var currentNightMode: Boolean = false
    var followSystem: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_new)
        currentNightMode = isNightMode(this)
        followSystem = SpeakerSharedPreferencesFactory.getNightModeFollowSystem(this)
        switch = findViewById(R.id.switchs)
        switch_Sys = findViewById(R.id.switchs1)

        switch_Sys!!.isChecked = followSystem


        if (followSystem) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            switch!!.visibility = View.GONE
        } else {
            switch!!.visibility = View.VISIBLE
            switch!!.isChecked = currentNightMode
            if (currentNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if (followSystem) {
            switch!!.setOnCheckedChangeListener(null)
        } else {
            switch!!.setOnCheckedChangeListener { compoundButton, b ->
                Log.i(TAG, "onCreate: switch is checked b: " + b)
                SpeakerSharedPreferencesFactory.setNightModeByManual(this,b)
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }


        switch_Sys!!.setOnCheckedChangeListener { compoundButton, b ->
            SpeakerSharedPreferencesFactory.setNightModeFollowSystem(this,b)
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                switch!!.visibility = View.GONE
            } else {
                switch!!.visibility = View.VISIBLE
                switch!!.isChecked = currentNightMode
                if (currentNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                switch!!.setOnCheckedChangeListener { compoundButton, b ->

                    SpeakerSharedPreferencesFactory.setNightModeByManual(this,b)
                    Log.i(TAG, "onCreate: switch is checked b: " + b)
                    if (b) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
        val textview = findViewById<TextView>(R.id.tv_title)
        textview.setOnClickListener {
            Toast.makeText(this,if (currentNightMode) "深色模式" else "浅色模式",Toast.LENGTH_LONG).show()
        }
        Log.i(TAG, "onCreate: " + isNightMode(this))

    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent: ")
    }


    /**
     * 判断当前模式
     */
    fun isNightMode(context: Context): Boolean {
        if (followSystem) {
            val currentNightMode =
                context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return currentNightMode == Configuration.UI_MODE_NIGHT_YES
        } else {
            return SpeakerSharedPreferencesFactory.getNightModeByManual(this)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i(TAG, "onConfigurationChanged: uiMode: " + newConfig.uiMode)
        super.onConfigurationChanged(newConfig)
        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        AppCompatDelegate.setDefaultNightMode(if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        when(currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> Toast.makeText(this,"关闭深色模式",Toast.LENGTH_LONG).show()
            Configuration.UI_MODE_NIGHT_YES -> Toast.makeText(this,"开启深色模式",Toast.LENGTH_LONG).show()
        }
    }
}