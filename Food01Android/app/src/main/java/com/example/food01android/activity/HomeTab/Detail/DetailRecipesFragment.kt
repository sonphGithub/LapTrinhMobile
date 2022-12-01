package com.example.food01android.activity.HomeTab.Detail

import android.content.Intent
import android.graphics.Rect
import com.squareup.picasso.Picasso
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
import com.example.food01android.model.Home.DataRecipeDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.BuildConfig
import com.example.food01android.FoodApi
import com.example.food01android.R
import com.example.food01android.activity.BaseFragment
import com.example.food01android.adapter.Detail.AdapterDetailRecipes
import com.example.food01android.databinding.ActivityDetailBinding
import com.example.food01android.model.Guide.GuideModel

class DetailRecipesFragment : BaseFragment() {

    lateinit var binding: ActivityDetailBinding
    lateinit var adapter: AdapterDetailRecipes
    lateinit var viewModel: DetailRecipesViewModel

    var recipe: DataRecipeDetail? = null
    val args: DetailRecipesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailRecipesViewModel::class.java]
        recipe = args.dataRecipesDetail
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityDetailBinding.inflate(inflater, container, false)
        setUpView()
        bind()
        return binding.root
    }

    fun setUpView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnBack) { view, windowInsets ->
            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }


        binding.listDetail.isNestedScrollingEnabled = true
        binding.txtName.isSelected = true
        recipe?.let { recipe ->
            Picasso.get().load(recipe.image)
                .into(binding.imageViewParallax)
            binding.txtName.text = recipe.name
            binding.ratingBar.rating = recipe.star.toFloat()
            configBtnFavorite(recipe.is_favorite)
            binding.listDetail.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterDetailRecipes(recipe, arrayListOf())
            binding.listDetail.adapter = adapter
        }

        binding.btnFav.setOnClickListener {
            if (recipe?.is_favorite == 0) {
                recipe?.is_favorite = 1
                configBtnFavorite(recipe?.is_favorite!!)
                binding.likeHeart.likeAnimation()
                saveFavorite()
            } else {
                recipe?.is_favorite = 0
                configBtnFavorite(recipe?.is_favorite!!)
                removeFavorite()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.btnShare.setOnClickListener{
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "#HealthyFood\n"
                shareMessage =
                    """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}                                       
                        """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {

            }
        }
    }

//    private fun getStatusBarHeight(): Int {
//        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
//        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
//        else Rect().apply { requireActivity().window.decorView.getWindowVisibleDisplayFrame(this) }.top
//    }

    private fun bind() {
        viewModel.token = token
        viewModel.recipeId = recipe?.id ?: 0
        viewModel.getRecommendLData()?.observe(viewLifecycleOwner, Observer { recommends ->
            adapter.setRecommend(recommends)
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
        val foodApi: FoodApi = FoodApi.invoke()
        recipe?.let {
            it.id?.let { it1 ->
                foodApi.saveFavorite(it1, token).enqueue(object : Callback<GuideModel> {
                    override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                        Log.d("save favorite success", "save" + response.body()?.code)
                    }

                    override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                        Log.d("post", "post fail" + t)
                    }
                })
            }
        }
    }

    private fun removeFavorite() {
        val foodApi: FoodApi = FoodApi.invoke()
        recipe?.let {
            it.id?.let { it1 ->
                foodApi.removeFavorite(it1, token).enqueue(object : Callback<GuideModel> {
                    override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                        Log.d("remove favorite success", "remove success" + response.body()?.message)
                    }

                    override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                        Log.d("post", "post fail" + t)
                    }
                })
            }
        }
    }

}