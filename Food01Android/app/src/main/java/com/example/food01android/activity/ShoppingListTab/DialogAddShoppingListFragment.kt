package com.example.food01android.activity.ShoppingListTab

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.food01android.databinding.ShoppingListDialogFragmentBinding


class DialogAddShoppingListFragment: DialogFragment() {

    lateinit var binding: ShoppingListDialogFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.example.food01android.R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShoppingListDialogFragmentBinding.inflate(inflater, container, false)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        requireDialog().window?.setWindowAnimations(com.example.food01android.R.style.DialogAnimation)

        return binding.root


    }


}