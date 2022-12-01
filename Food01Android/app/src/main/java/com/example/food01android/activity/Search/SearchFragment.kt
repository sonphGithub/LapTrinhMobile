package com.example.food01android.activity.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.FoodApi
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.HomeTab.Explore.ExploreViewModel
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Guide.ClickInterFace


import com.example.food01android.adapter.Home.AdapterSearchResult
import com.example.food01android.adapter.Home.AdapterSuggestKey
import com.example.food01android.databinding.FragmentSearchBinding
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Search.DataSearch
import com.example.food01android.model.Search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var adapterSuggest: AdapterSuggestKey
    lateinit var adapterResult: AdapterSearchResult
    lateinit var viewModel: SearchViewModel
    private var isLoadmore: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setUpView()
        bind()
        return binding.root
    }

    private fun setUpView() {
        (activity as MainActivity).hideBottomBar()
        showKeyboard()
        binding.listResult.visibility = View.INVISIBLE
        binding.listSuggest.visibility = View.VISIBLE
        binding.viewSearchEmpty.visibility = View.GONE

        //Suggets==========
        adapterSuggest =
            AdapterSuggestKey(arrayListOf(), object : AdapterSuggestKey.TagClickInterface {
                override fun tagClick(keyword: String) {
                    hideKeyboard()
                    binding.loadingView.visibility = View.VISIBLE
                    binding.searchBar.setText(keyword)
                    binding.searchBar.setSelection(binding.searchBar.length())
                    viewModel.getSearch(keyword)
                    binding.listResult.visibility = View.VISIBLE
                    binding.listSuggest.visibility = View.INVISIBLE
                }
            })
        binding.listSuggest.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listSuggest.adapter = adapterSuggest


        //Search=========
        adapterResult =
            AdapterSearchResult(viewModel.recipes, object : ClickInterFace {
                override fun onClick(position: Int) {
                    val action =
                        viewModel.recipes[position].id?.let {
                            SearchFragmentDirections.actionNavigationSearchToNavigationPreview(
                                it
                            )
                        }
                    action?.let { findNavController().navigate(it) }
                }
            })
        binding.listResult.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listResult.adapter = adapterResult

        binding.listResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!binding.listResult.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (viewModel.nextPageUrl != "") {
                        if (!isLoadmore) {
                            isLoadmore = true
                            binding.loadMoreView.visibility = View.VISIBLE
                            viewModel.loadMore()
                        }
                    }
                }
            }
        })

        searchViewClickListener()
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.btnBack) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }


    private fun bind() {
        viewModel.token = token
        viewModel.getSuggetLiveData()?.observe(viewLifecycleOwner, Observer { suggest ->
            adapterSuggest.setContent(suggest)
            binding.loadingView.visibility = View.GONE
        })

        viewModel.getSearchLiveData()?.observe(viewLifecycleOwner, Observer { result ->
            isLoadmore = false
            binding.loadingView.visibility = View.GONE
            binding.loadMoreView.visibility = View.GONE
            adapterResult.setContent(result)
            if (viewModel.recipes.isEmpty()) {
                binding.viewSearchEmpty.visibility = View.VISIBLE
                binding.listSuggest.visibility = View.INVISIBLE
                binding.listResult.visibility = View.INVISIBLE
            } else {
                binding.viewSearchEmpty.visibility = View.GONE
                binding.listSuggest.visibility = View.INVISIBLE
                binding.listResult.visibility = View.VISIBLE
            }
        })

    }

    private fun searchViewClickListener() {
        binding.searchBar.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        //action submit
                        hideKeyboard()
                        binding.loadingView.visibility = View.VISIBLE
                        viewModel.recipes.clear()
                        adapterResult.setContent(viewModel.recipes)
                        binding.viewSearchEmpty.visibility = View.GONE

                        if (binding.searchBar.text.toString() == "") {
                            binding.loadingView.visibility = View.GONE
                            binding.viewSearchEmpty.visibility = View.VISIBLE
                        }

                        if (viewModel.recipes.isEmpty()) {
                            viewModel.getSearch(binding.searchBar.text.toString())
                            binding.searchBar.setSelection(binding.searchBar.length())
                            binding.listSuggest.visibility = View.INVISIBLE
                            binding.listResult.visibility = View.VISIBLE
                        }
                    }
                }
                false
            }
        }
    }
}