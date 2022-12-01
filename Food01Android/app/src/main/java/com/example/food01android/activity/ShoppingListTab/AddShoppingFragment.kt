package com.example.food01android.activity.ShoppingListTab

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.food01android.databinding.AddShoppinglistSheetBinding

class AddShoppingFragment: SuperBottomSheetFragment() {
    lateinit var binding: AddShoppinglistSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = AddShoppinglistSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun getCornerRadius(): Float {
        return 60f
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return -50
    }

}