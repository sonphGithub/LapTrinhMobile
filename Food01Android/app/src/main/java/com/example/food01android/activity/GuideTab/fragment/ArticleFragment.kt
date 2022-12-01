package com.example.food01android.activity.GuideTab.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.food01android.FoodApi
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.GuideTab.viewModel.ArticleViewModel
import com.example.food01android.activity.GuideTab.viewModel.GuideViewModel

import com.example.food01android.activity.MainActivity
import com.example.food01android.databinding.ArticleFragmentBinding

import com.example.food01android.model.Guide.DataDetailArticle
import com.example.food01android.model.Guide.DetailArticle
import com.example.food01android.model.Guide.DetailGuideData
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleFragment: BaseFragment() {

    lateinit var binding: ArticleFragmentBinding
    lateinit var viewModel: ArticleViewModel
    val args: ArticleFragmentArgs by navArgs()
    var detailGuide: DetailGuideData = DetailGuideData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ArticleFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        setUpView()
        bind()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setUpView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnBack) { view, windowInsets ->
            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.btnShare) { view, windowInsets ->
            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        (activity as MainActivity).hideBottomBar()
        detailGuide = args.detailGuide
        Picasso.get().load(detailGuide.image).into(binding.imageView)
        binding.txtTitle.text = args.detailGuide.title

        if (args.part == 0){
            binding.txtPart.text = "Part 1"
        }else{
            binding.txtPart.text = "Part ${args.part}"
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

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
    }

    private fun bind() {
        viewModel.token = token
        viewModel.articleId = args.detailGuide.id
        viewModel.getArticleLiveData()?.observe(viewLifecycleOwner, Observer{ article ->
            configWebView(article.content)
            binding.loadingView.visibility = View.GONE
        })
    }



    fun configWebView(content: String) {
        val html = """
                <html>
                    <head>
                        <meta content="width=device-width, initial-scale=1, minimum-scale=1, user-scalable=no" name="viewport">
                        <style>
                        body {
                            -webkit-text-size-adjust: none;
                            padding: 10px;
                        }
                        p {
                            font-family: "Helvetica Neue";
                            font-size:18px;
                        }
                        h2 {
                            font-family: "Helvetica Neue";
                            font-size: 20px;
                        }
                        img {
                            max-width: 100%;
                            height : auto;
                            margin: 0;
                        }
                        </style>
                    </head>
                    <body>
                        ${content}
                    </body>
                </html>
            """
        binding.webView.loadData(html, "text/html", "UTF-8")





    }

}