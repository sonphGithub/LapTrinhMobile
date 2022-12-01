package com.example.food01android.adapter.Setting

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView

import com.example.food01android.R
import com.example.food01android.databinding.RowSettingBinding
import com.example.food01android.model.Home.Home
import com.facebook.FacebookSdk.getApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface SettingInterface {
    fun setvegan(vegan: Int)
}

class AdapterRecyclerViewSetting(val listener: SettingInterface) :
    RecyclerView.Adapter<AdapterRecyclerViewSetting.SettingViewHolder>() {

    class SettingViewHolder(val binding: RowSettingBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    var settingList = arrayListOf(
        "Rate us",
        "Tell a friend about the app",
        "Vegetarian", "Term of Use",
        "Privacy Policy", "Feedback",
        "Sign Out"
    )

//    var sharedPreferences: SharedPreferences =
//        getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE)

    var images: ArrayList<Int> = arrayListOf(
        R.drawable.ic_setting_rateus,
        R.drawable.ic_setting_abouttheapp,
        R.drawable.ic_setting_vegetarian,
        R.drawable.ic_setting_termofuse,
        R.drawable.ic_setting_privacy,
        R.drawable.ic_setting_feedback,
        R.drawable.ic_setting_logout
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowSettingBinding.inflate(layoutInflater, parent, false)
        return SettingViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.binding.tvTitleSetting.text = settingList[position]
        holder.binding.imgViewIconSetting.setImageResource(images[position])
//        holder.binding.btnSwitch.isChecked = sharedPreferences.getInt("vegan", 0) != 0
//        Log.d("vegan ID", "" + sharedPreferences.getInt("vegan", 0))

        if (position == settingList.lastIndex) {
            holder.binding.tvTitleSetting.setTextColor("#FF4848".toColorInt())
            holder.binding.btnSwitch.visibility = View.GONE
            holder.binding.lineViewSetting.visibility = View.GONE
        } else if (position == 2) {

            holder.binding.btnSwitch.setOnClickListener {
                if (holder.binding.btnSwitch.isChecked) {
                    listener.setvegan(1)
                } else {
                    listener.setvegan(0)
                }
            }
        } else {
            holder.binding.btnSwitch.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return settingList.size
    }
}