package com.example.food01android.activity.FavoriteTab

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.FavoriteTab.fragment.CommunityFragment
import com.example.food01android.activity.FavoriteTab.fragment.MyCollectionFragment
import com.example.food01android.activity.FavoriteTab.viewModel.FavoriteViewModel
import com.example.food01android.activity.HomeTab.Home.HomeFragmentDirections
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Favorite.FavoriteAdapter
import com.example.food01android.databinding.FavoriteFragmentBinding

enum class FavoriteMode {
    Collection,
    Community
}

class FavoriteFragment : BaseFragment() {

    private lateinit var viewModel: FavoriteViewModel
    lateinit var binding: FavoriteFragmentBinding
    lateinit var adapter: FavoriteAdapter
    private lateinit var collectionFragment: MyCollectionFragment
    private lateinit var communityFragment: CommunityFragment
    var mode: FavoriteMode = FavoriteMode.Collection

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        (activity as MainActivity).showBottomBar()
        setUpView()
        return binding.root
    }

    fun setUpView() {
        configModeColor()

        if (!::collectionFragment.isInitialized) {
            collectionFragment = MyCollectionFragment()
        }
        if (!::communityFragment.isInitialized) {
            communityFragment = CommunityFragment()
        }

        adapter = FavoriteAdapter(arrayListOf(collectionFragment, communityFragment), this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0){
                    mode = FavoriteMode.Collection
                }else{
                    mode = FavoriteMode.Community
                }
                configModeColor()
            }
        })

        binding.viewPager.isUserInputEnabled = true

        binding.btnMyCollection.setOnClickListener {
            mode = FavoriteMode.Collection
            configModeColor()
            binding.viewPager.currentItem = 0
        }

        binding.btnCommunity.setOnClickListener {
            mode = FavoriteMode.Community
            configModeColor()
            binding.viewPager.currentItem = 1
        }

        binding.btnSearchHome.setOnClickListener{
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationSearch()
            findNavController().navigate(action)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

    }

    fun configModeColor() {
        if (mode == FavoriteMode.Collection) {
            binding.btnCommunity.setTextColor(Color.parseColor("#994E4E4E"))
            binding.btnCommunity.setBackgroundColor(Color.parseColor("#26706466"))
            binding.btnMyCollection.setTextColor(Color.parseColor("#5BC25A"))
            binding.btnMyCollection.setBackgroundColor(Color.parseColor("#4D5BC25A"))
        } else {
            binding.btnCommunity.setTextColor(Color.parseColor("#5BC25A"))
            binding.btnCommunity.setBackgroundColor(Color.parseColor("#4D5BC25A"))
            binding.btnMyCollection.setTextColor(Color.parseColor("#994E4E4E"))
            binding.btnMyCollection.setBackgroundColor(Color.parseColor("#26706466"))
        }
    }

//    fun checkData() {
//        if (recipes.isEmpty()) {
//
//            viewModel.isFavoriteMode.observe(viewLifecycleOwner, Observer {
//                getData()
//            })
//        } else {
//            binding.btnMyCommunity.setOnClickListener {
//                viewModel.isFavoriteMode.value = FavoriteMode.Community
//                if (viewModel.isFavoriteMode.value == FavoriteMode.Collection) {
//                    binding.btnMyCollection.setTextColor(Color.parseColor("#5BC25A"))
//                    binding.btnMyCommunity.setTextColor(Color.parseColor("#000000"))
//                    recipes.clear()
//                    getMyFav()
//                } else {
//                    binding.btnMyCollection.setTextColor(Color.parseColor("#000000"))
//                    binding.btnMyCommunity.setTextColor(Color.parseColor("#5BC25A"))
//                    recipes.clear()
//                    getFavCommunity()
//                }
//            }
//
//
//            binding.btnMyCollection.setOnClickListener {
//                viewModel.isFavoriteMode.value = FavoriteMode.Collection
//                if (viewModel.isFavoriteMode.value == FavoriteMode.Collection) {
//                    binding.btnMyCollection.setTextColor(Color.parseColor("#5BC25A"))
//                    binding.btnMyCommunity.setTextColor(Color.parseColor("#000000"))
//                    recipes.clear()
//                    getMyFav()
//                } else {
//                    binding.btnMyCollection.setTextColor(Color.parseColor("#000000"))
//                    binding.btnMyCommunity.setTextColor(Color.parseColor("#5BC25A"))
//                    recipes.clear()
//                    getFavCommunity()
//                }
//            }
//        }
//    }
//

}




