package com.example.food01android.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.adapter.Setting.AdapterRecyclerViewSetting
import com.example.food01android.databinding.SettingFragmentBinding
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.food01android.FoodApi
import com.example.food01android.R
import com.example.food01android.adapter.Setting.SettingInterface
import com.example.food01android.model.Home.Home
import com.example.food01android.model.Settings.User
import com.example.food01android.model.Settings.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SettingFragment : BaseFragment(), SettingInterface {
    private lateinit var binding: SettingFragmentBinding
    var users: UserInfo? = null
    var test : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->
            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

//        val layoutAnimationController: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.down_to_up)
//        binding.recyclerViewSetting.layoutAnimation = layoutAnimationController
        binding.recyclerViewSetting.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSetting.adapter = AdapterRecyclerViewSetting(this)


//        if (users == null){
//            getUserInfo()
//        }else{
//
//        }
        return binding.root
    }

    fun getUserInfo() {
        test = 1
        val foodApi: FoodApi = FoodApi.invoke()
        Log.e("getUserInfo","test = $test")
        foodApi.getUserInfo(token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                users = response.body()!!.data
//Log.d("")
//                response.body()?.data?.let{
//                    users = it
//                }

                binding.tvUserEmail.text = users!!.email
                binding.tvUserName.text = users!!.name
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Setting", "error getdata " + t)
            }
        })
    }

    fun setVegan(vegan: Int) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.setVegan(token, vegan).enqueue(object : Callback<Home> {
            override fun onResponse(call: Call<Home>, response: Response<Home>) {
                Log.d("setVegan", "success" + response.body()?.data.toString())
            }

            override fun onFailure(call: Call<Home>, t: Throwable) {
                Log.d("setVegan", "error getdata " + t)
            }
        })
    }



    override fun setvegan(vegan: Int) {
        setVegan(vegan)
    }


}