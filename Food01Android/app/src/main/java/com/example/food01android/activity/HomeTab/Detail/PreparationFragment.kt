package com.example.food01android.activity.HomeTab.Detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.food01android.adapter.Detail.AdapterViewPagerPrepareration
import com.example.food01android.databinding.FragmentPreparationBinding
import com.example.food01android.model.Home.Methods
import com.example.food01android.third.ZoomOutPageTransformer
import kotlin.collections.ArrayList

class PreparationFragment : Fragment() {

    lateinit var binding: FragmentPreparationBinding
    lateinit var adapter: AdapterViewPagerPrepareration
    val args: PreparationFragmentArgs by navArgs()
    var methods: ArrayList<Methods> = arrayListOf()
    var currentIndex: Int = 0


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreparationBinding.inflate(inflater, container, false)
        methods.addAll(args.methods)
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        adapter = AdapterViewPagerPrepareration(methods)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = adapter
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
                binding.txtStep.text = "STEP ${position + 1} TO ${methods.size}"
            }
        })

    //action Next
        binding.btnNext.setOnClickListener{
            if (currentIndex  < methods.size){
                currentIndex += 1
            }
            binding.viewPager.currentItem = currentIndex


            if (currentIndex == methods.size){
//                binding.btnNext.setCardBackgroundColor(Color.parseColor("#CECECE"))
                binding.txtNext.text = "Finish"
            }else{
                binding.btnNext.setCardBackgroundColor(Color.parseColor("#5BC25A"))
                binding.txtNext.text = "Next"
            }

            if (currentIndex == 0){
                binding.btnPrev.setCardBackgroundColor(Color.parseColor("#CECECE"))
            }else{
                binding.btnPrev.setCardBackgroundColor(Color.parseColor("#5BC25A"))
            }

            if(binding.txtNext.text == "Finish"){
                findNavController().popBackStack()
            }

        }

        //action Previous
        binding.btnPrev.setOnClickListener{
            if (currentIndex > 0){
                currentIndex -= 1
            }
            binding.viewPager.currentItem = currentIndex
            if (currentIndex == 0){
                binding.btnPrev.setCardBackgroundColor(Color.parseColor("#CECECE"))
            }else{
                binding.btnPrev.setCardBackgroundColor(Color.parseColor("#5BC25A"))
            }

            if (currentIndex == methods.size){
                binding.btnNext.setCardBackgroundColor(Color.parseColor("#CECECE"))
            }else{
                binding.btnNext.setCardBackgroundColor(Color.parseColor("#5BC25A"))
            }
        }

        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }



}