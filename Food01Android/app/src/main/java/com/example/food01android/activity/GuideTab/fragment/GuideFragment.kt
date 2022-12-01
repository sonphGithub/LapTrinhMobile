package com.example.food01android.activity.GuideTab.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.GuideTab.viewModel.GuideViewModel
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.adapter.Guide.GuideAdapter
import com.example.food01android.databinding.GuideFragmentBinding

class GuideFragment : BaseFragment() {
    lateinit var viewModel: GuideViewModel
    lateinit var binding: GuideFragmentBinding
    lateinit var adapter: GuideAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GuideFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[GuideViewModel::class.java]
        setUpView()
        bind()
        return binding.root
    }

    private fun setUpView() {
        (activity as MainActivity).showBottomBar()
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        adapter = GuideAdapter(arrayListOf(), object: ClickInterFace{
            override fun onClick(position: Int) {
                val action = GuideFragmentDirections.actionNavigationGuideToNavigationDetailGuide(viewModel.guides[position].id, viewModel.guides[position].name)
                findNavController().navigate(action)
            }

        })
        binding.listGuide.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listGuide.adapter = adapter
    }

    fun bind(){
        viewModel.token = token
        viewModel.getGuideLiveData()?.observe(viewLifecycleOwner, Observer{ guide ->
            adapter.setGuide(guide)
            binding.loadingView.visibility = View.GONE
        })
    }

}