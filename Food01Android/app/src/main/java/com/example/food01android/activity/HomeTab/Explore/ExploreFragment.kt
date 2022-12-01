package com.example.food01android.activity.HomeTab.Explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.food01android.databinding.FragmentExploreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.food01android.FoodApi
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Favorite.CommunityAdapter
import com.example.food01android.adapter.Guide.ClickInterFace

import com.example.food01android.model.Guide.GuideModel

class ExploreFragment : BaseFragment() {

    lateinit var binding: FragmentExploreBinding
    lateinit var adapter: CommunityAdapter
    lateinit var viewModel: ExploreViewModel
    val args: ExploreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).hideBottomBar()
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ExploreViewModel::class.java]
        setUpView()
        bind()
        return binding.root
    }

    fun setUpView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }
        binding.txtTitle.text = args.title
        adapter =
            CommunityAdapter(arrayListOf(), object : ClickInterFace {
                override fun onClick(position: Int) {
                    val action =
                        viewModel.explores[position].id?.let {
                            ExploreFragmentDirections.actionNavigationExploreToNavigationPreview(
                                it
                            )
                        }
                    action?.let { findNavController().navigate(it) }
                }
            }, object : CommunityAdapter.SaveFavoriteInterface {
                override fun save(position: Int) {
                    saveFavorite(position)
                }
            }, object : CommunityAdapter.RemoveFavoriteInterface {
                override fun remove(position: Int) {
                    removeFavorite(position)
                }
            }
            )

        binding.listExplore.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listExplore.adapter = adapter

        binding.btnBackExplore.setOnClickListener {
            findNavController().popBackStack()
        }


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun bind() {
        viewModel.categoryId = args.categoryId
        viewModel.token = token
        viewModel.getExploreLiveData()?.observe(viewLifecycleOwner, Observer{ recipes ->
            adapter.setContent(recipes)
        })
    }

    fun saveFavorite(recipeId: Int) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.saveFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("save favorite success", "save" + response.body()?.message)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    fun removeFavorite(recipeId: Int) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.removeFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("remove favorite success", "remove success" + response.body()?.message)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }


}