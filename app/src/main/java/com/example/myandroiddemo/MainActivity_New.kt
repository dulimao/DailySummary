package com.example.myandroiddemo

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity

private const val TAG = "MainActivity_New"

//【【【RemoteViews】】】 看作是 layout文件中所包含的全部视图的集合
//【【【RemoteViewsService】】】，是管理RemoteViews的服务。
        //(01) 通过setRemoteAdapter来设置 “RemoteViews对应RemoteViewsService”。
        //(02) 之后在RemoteViewsService中，实现RemoteViewsFactory接口。然后，在RemoteViewsFactory接口中对“集合视图”的各个子项进行设置(“集合视图”的各个子项：例如，GridView的每一个格子都是一个子项；ListView中的每一列也是一个子项)。
        //RemoteViewsFactory
        //通过RemoteViewsService中的介绍，我们可以了解“RemoteViewsService是通过RemoteViewsFactory来具体管理layout中集合视图的”，即“RemoteViewsFactory管理集合视图的实施者”。
//【【【RemoteViewsFactory】】】是RemoteViewsService中的一个接口。RemoteViewsFactory提供了一系列的方法管理“集合视图”中的每一项。例如：
        //(01)RemoteViews getViewAt(int position)
        //通过getViewAt()来获取“集合视图”中的第position项的视图，视图是以RemoteViews的对象返回的。
        //(02)int getCount()
        //通过getCount()来获取“集合视图”中所有子项的总数。
        //
        //因此，我们可以将 “RemoteViewsFactory 看作是 layout中集合视图管理的具体实施者”。
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