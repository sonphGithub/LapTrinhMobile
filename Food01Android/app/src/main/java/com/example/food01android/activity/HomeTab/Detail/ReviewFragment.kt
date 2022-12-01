package com.example.food01android.activity.HomeTab.Detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.food01android.activity.MainActivity
import com.example.food01android.databinding.FragmentReviewsBinding
import com.squareup.picasso.Picasso
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.FoodApi
import com.example.food01android.R
import com.example.food01android.activity.BaseFragment
import com.example.food01android.adapter.Detail.AdapterRecyclerViewReviews
import com.example.food01android.model.Home.DataReview
import com.example.food01android.model.Home.DataReviewObject
import com.example.food01android.model.Home.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewFragment : BaseFragment() {

    lateinit var binding: FragmentReviewsBinding
    lateinit var adapterRecyclerViewReview: AdapterRecyclerViewReviews
    val args: ReviewFragmentArgs by navArgs()
    var reviews: ArrayList<Review> = arrayListOf()
    var rating: Float? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewsBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomBar()
        binding.recyclerViewReview.isVisible = false
        binding.editText.isVisible = true
        rating = args.rating
        configUI()
        actionBack()
        actionDone()
        tapToRate()
        return binding.root
    }

    fun configUI() {
        Picasso.get().load(args.dataRecipesDetail.image)
            .into(binding.imageViewReview)
        binding.tvNameRecipeReview.text = args.dataRecipesDetail.name
        binding.ratingbarReview.rating = args.dataRecipesDetail.star.toFloat()
        binding.tvNameRecipeReview.setSelected(true)
        binding.editText.requestFocus()
        binding.editText.setOnFocusChangeListener { view, hasfocus ->
            if (hasfocus) {
                val imm: InputMethodManager? =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.editText.setTypeface(resources.getFont(R.font.helvetica_regular))
        }
        binding.editText.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding.editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.ratingBarBottom.rating = rating!!
    }

    fun getReviewInRecipe() {
        val foodApi: FoodApi = FoodApi.invoke()
        args.dataRecipesDetail.id?.let {
            foodApi.getReviewInRecipes(token, it)
                .enqueue(object : Callback<DataReview> {
                    override fun onResponse(call: Call<DataReview>, response: Response<DataReview>) {
                        reviews.addAll(response.body()!!.data)
                        Log.e("review", ""+ response.body()!!.data)
                        binding!!.recyclerViewReview.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapterRecyclerViewReview = AdapterRecyclerViewReviews(reviews)
                        binding.recyclerViewReview.adapter = adapterRecyclerViewReview
                        adapterRecyclerViewReview.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<DataReview>, t: Throwable) {
                        Log.e("get review fail", "" + t)
                    }

                })
        }
    }

    fun submitReview(){
        val foodApi: FoodApi = FoodApi.invoke()
        args.dataRecipesDetail.id?.let {
            foodApi.setReview(token, it, this.rating!!.toInt(),
                binding.editText.text.toString()
            ).enqueue(object : Callback<DataReviewObject>{
                override fun onResponse(call: Call<DataReviewObject>, response: Response<DataReviewObject>) {
                    getReviewInRecipe()
                    Log.e("set review success", "" + response.body()!!.message)
                }

                override fun onFailure(call: Call<DataReviewObject>, t: Throwable) {
                    Log.e("set review fail", "" + t)

                }
            })
        }
    }

    fun tapToRate(){
        binding.ratingBarBottom.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            this.rating = rating
            binding.ratingBarBottom.rating = this.rating!!
        }
    }


    fun actionBack() {
        binding.btnBackReview.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun actionDone() {
        binding.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitReview()
                binding.recyclerViewReview.isVisible = true
                binding.editText.isVisible = false
            }
            false
        }
    }



}
