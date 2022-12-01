package com.example.food01android.activity.ShoppingListTab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.BuildConfig
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.ShoppingList.ShoppingListAdapter
import com.example.food01android.adapter.ShoppingList.ShoppingListInterface
import com.example.food01android.databinding.FragmentShoppingListBinding
import com.example.food01android.manager.DBManager

class ShoppingListFragment : BaseFragment() {

    lateinit var binding: FragmentShoppingListBinding
    lateinit var adapter: ShoppingListAdapter
    private lateinit var viewModel: ShoppingListViewModel
    var dbManager: DBManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ShoppingListViewModel::class.java]
        dbManager = DBManager()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        (activity as MainActivity).showBottomBar()
        setUpView()
        bind()
        return binding.root
    }

    fun setUpView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, windowInsets ->

            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                if (insets.top > 0) {
                    topMargin = insets.top
                }
            }
            WindowInsetsCompat.CONSUMED
        }

        //config adapter
        adapter = ShoppingListAdapter(arrayListOf(), object : ShoppingListInterface {
            override fun onClick(position: Int) {
                Log.e("click", "click")
            }

            override fun onClickDelete(id: Int, position: Int) {
                viewModel.listRecipes().removeAt(position)
                dbManager?.deleteRecipeToShoppingList(id)
                adapter.notifyItemRemoved(position)
            }

            override fun onClickShare(id: Int, recipeName: String) {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    var shareMessage = "#HealthyFood\n $recipeName\n"
                    shareMessage =
                        """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}                                       
                        """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {

                }
            }
        })
        binding.listView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.listView.adapter = adapter

        //action
        binding.btnAddItem.setOnClickListener {
            AddShoppingFragment().show(requireActivity().supportFragmentManager, "")
//            val dialog = DialogAddShoppingListFragment()
//            dialog.show(requireActivity().supportFragmentManager, "customDialog")
        }
    }

    fun bind() {
        viewModel.getRecipes()?.observe(viewLifecycleOwner, Observer {
            adapter.setContent(it)
            if (it.isEmpty()) {
                binding.viewSearchEmpty.visibility = View.VISIBLE
                binding.listView.visibility = View.GONE
            } else {
                binding.viewSearchEmpty.visibility = View.GONE
                binding.listView.visibility = View.VISIBLE
            }
        })
    }


}





