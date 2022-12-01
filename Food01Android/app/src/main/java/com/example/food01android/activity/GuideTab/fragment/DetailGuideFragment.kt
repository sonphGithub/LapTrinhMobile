package com.example.food01android.activity.GuideTab.fragment

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.GuideTab.viewModel.DetailGuideViewModel
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.adapter.Guide.DetailGuideAdapter
import com.example.food01android.databinding.ActivityCategoriesGuideBinding

class DetailGuideFragment: BaseFragment() {

    lateinit var binding: ActivityCategoriesGuideBinding
    lateinit var adapter: DetailGuideAdapter
    lateinit var viewModel: DetailGuideViewModel
    val args: DetailGuideFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityCategoriesGuideBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DetailGuideViewModel::class.java]
        setUpView()
        bind()
        return binding.root
    }

    private fun setUpView() {
        (activity as MainActivity).hideBottomBar()
        binding.txtTitle.text = args.title

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        adapter = DetailGuideAdapter( arrayListOf(), object: ClickInterFace{
            override fun onClick(position: Int) {
                val action = DetailGuideFragmentDirections.actionNavigationDetailGuideToNavigationArticle(viewModel.detailGuides[position],0)
                findNavController().navigate(action)
            }
        })

        binding.listDetailGuide.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.listDetailGuide.adapter = adapter

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
    }

    private fun bind() {
        viewModel.categoryId = args.categoryId
        viewModel.token = token
        viewModel.getDetailGuideLiveData()?.observe(viewLifecycleOwner, Observer{ guides ->
            adapter.setGuide(guides)
        })
    }



}