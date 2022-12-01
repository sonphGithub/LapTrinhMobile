package com.example.food01android.activity.HomeTab.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Home.AdapterParent
import com.example.food01android.databinding.HomeFragmentBinding
import com.example.food01android.model.Home.*


class HomeFragment : BaseFragment() {

    lateinit var binding: HomeFragmentBinding
    lateinit var adapter: AdapterParent
    lateinit var viewModel: HomeViewModel
    var isLoadmore: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomBar()
        setUpView()
        bind()
        return binding.root
    }

    fun setUpView() {
        binding.listHome.visibility = View.INVISIBLE

        adapter = AdapterParent(DataX(), arrayListOf())
        binding.listHome.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listHome.adapter = adapter

        loadMore()
        refresh()

        binding.btnSearchHome.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationSearch()
            findNavController().navigate(action)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }


    }

    fun bind() {
        viewModel.token = token
        viewModel.getHomeLiveData()?.observe(viewLifecycleOwner, Observer { home ->
            adapter.setHome(home)
            binding.listHome.visibility = View.VISIBLE
            binding.loadingView.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
            binding.swipeRefresh.isEnabled = true
        })

        viewModel.loadMoreCate()?.observe(viewLifecycleOwner, Observer { cate ->
            isLoadmore = false
            adapter.setCate(cate)
            binding.loadMoreView.visibility = View.GONE
        })
    }

    fun loadMore() {
        binding.listHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!binding.listHome.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
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
    }

    fun refresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.home = DataX()
            adapter.notifyDataSetChanged()
            bind()
        }
    }

}