package com.example.food01android.activity.ShoppingListTab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.manager.DBManager
//import com.example.food01android.manager.RecipeObject
import com.example.food01android.model.Home.DataRecipeDetail

class ShoppingListViewModel: ViewModel() {

    var dbManager: DBManager? = null

    var recipes: MutableLiveData<ArrayList<DataRecipeDetail>>? = null


    fun listRecipes(): ArrayList<DataRecipeDetail> {
        return recipes?.value ?: arrayListOf()
    }

    fun getRecipes(): LiveData<ArrayList<DataRecipeDetail>>? {
        if (recipes == null) {
            recipes = MutableLiveData()
            loadRecipeFromDB()
        }
        return recipes
    }

    fun resume(){
        loadRecipeFromDB()
    }
    fun destroy(){
        dbManager = null
        recipes?.value?.clear()
    }

    private fun loadRecipeFromDB(){
        if (dbManager == null){
            dbManager = DBManager()
        }
        recipes?.value = dbManager?.getRecipeDB()
    }
}