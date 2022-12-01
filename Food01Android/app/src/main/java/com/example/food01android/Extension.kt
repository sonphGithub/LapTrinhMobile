
package com.example.food01android

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.food01android.third.MyBounceInterpolator


fun View.bounce(context: Context){
    val anim = AnimationUtils.loadAnimation(context, R.anim.bounce)
    val interpolator = MyBounceInterpolator(0.2, 20.0)
    anim.interpolator = interpolator
    this.startAnimation(anim)
}


fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}



fun Activity.transparentStatusAndNavigation(
    systemUiScrim: Int = Color.parseColor("#00000000") // 25% black
) {
    var systemUiVisibility = 0
    // Use a dark scrim by default since light status is API 23+
    var statusBarColor = systemUiScrim
    //  Use a dark scrim by default since light nav bar is API 27+
    var navigationBarColor = systemUiScrim
    val winParams = window.attributes


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        navigationBarColor = Color.TRANSPARENT
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        systemUiVisibility = systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = systemUiVisibility
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        winParams.flags = winParams.flags or
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        winParams.flags = winParams.flags and
                (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION).inv()
        window.statusBarColor = statusBarColor
        window.navigationBarColor = navigationBarColor
    }

    window.attributes = winParams
}


