package com.example.food01android.activity.HomeTab.Preview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.food01android.*
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.MainActivity
import com.example.food01android.databinding.ActivityPreviewBinding
import com.example.food01android.model.Guide.GuideModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.Path


class PreviewRecipesFragment : BaseFragment() {

    lateinit var binding: ActivityPreviewBinding
    lateinit var viewModel: PreviewViewModel
    var recipeId: Int = 0
    private val args: PreviewRecipesFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PreviewViewModel::class.java]
        recipeId = args.recipeId
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityPreviewBinding.inflate(inflater, container, false)
        setUpView()
        bind()
        //configLayout


        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }




    private fun setUpView() {
        (activity as MainActivity).transparentStatusAndNavigation()
        (activity as MainActivity).hideBottomBar()
        binding.txtName.isSelected = true
        binding.tViewResultPreview.isSelected = true
        binding.tViewTotalTimePreview.isSelected = true
        zoom()

      //action
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    fun bind() {
        viewModel.recipeId = recipeId
        viewModel.token = token
        viewModel.getDetailLiveData()?.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe.about == null || recipe.about == "") {
                binding.txtAbout.text = "This content will be updated soon..."
            } else {
                binding.txtAbout.text = recipe.about
            }
            binding.tViewTotalTimePreview.text = recipe.total_time
            binding.tViewResultPreview.text = recipe.result
            binding.txtName.text = recipe.name
            binding.ratingbarPreview.rating = recipe.star.toFloat()
            Picasso.get().load(recipe.image)
                .into(binding.imageViewRecipes)
            configBtnFavorite(recipe.is_favorite)


            binding.btnDetail.setOnClickListener {
                val action =
                    PreviewRecipesFragmentDirections.actionNavigationPreviewToNavigationDetailRecipes(
                        recipe)
                findNavController().navigate(action)
            }

            binding.btnFav.setOnClickListener {
                if (recipe.is_favorite == 0) {
                    recipe.is_favorite = 1
                    configBtnFavorite(recipe.is_favorite)
                    binding.likeHeart.likeAnimation()
                    saveFavorite()
                } else {
                    recipe.is_favorite = 0
                    configBtnFavorite(recipe.is_favorite)
                    removeFavorite()
                }
            }
        })
    }

    private fun configBtnFavorite(isFavorite: Int) {
        if (isFavorite == 0) {
            binding.btnFav.setImageResource(R.drawable.ic_btn_fav_detail_empty)
        } else {
//            binding.btnFav.bounce(requireContext())
            binding.btnFav.setImageResource(R.drawable.ic_btn_fav_detail_fill)
        }
    }

    private fun saveFavorite() {
        val foodApi: FoodApi = Retrofit.Builder()
            .baseUrl("http://food01.sboomtools.net:81/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)

        foodApi.saveFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("save favorite success", "save" + response.body()?.message)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    private fun removeFavorite() {
        val foodApi: FoodApi = Retrofit.Builder()
            .baseUrl("http://food01.sboomtools.net:81/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)

        foodApi.removeFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("remove favorite success", "remove success" + response.body()?.message)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    private fun scale(value: Float, scaleType: AnimatorSet) {
        val scaleDownX = ObjectAnimator.ofFloat(binding.imageViewRecipes, "scaleX", value)
        val scaleDownY = ObjectAnimator.ofFloat(binding.imageViewRecipes, "scaleY", value)
        scaleDownX.duration = 5000
        scaleDownY.duration = 5000
        scaleType.play(scaleDownX).with(scaleDownY)
    }

    private fun zoom() {
        val scaleUp = AnimatorSet()
        val scaleDown = AnimatorSet()
        val animatorSet = AnimatorSet()
        scale(1.3f, scaleUp)
        scale(1f, scaleDown)
        animatorSet.playSequentially(scaleUp, scaleDown)
        animatorSet.duration = 7000
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            private var mCanceled = false
            override fun onAnimationStart(animation: Animator) {
                mCanceled = false
            }

            override fun onAnimationCancel(animation: Animator) {
                mCanceled = true
            }

            override fun onAnimationEnd(animation: Animator) {
                if (!mCanceled) {
                    animation.start()
                }
            }
        })
        animatorSet.start()
    }
}




