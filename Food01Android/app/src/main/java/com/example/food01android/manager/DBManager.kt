package com.example.food01android.manager

import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.Ingredients
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where

class DBManager {

    var realm: Realm

    init {
        val realmName = "FoodApp"
        val config = RealmConfiguration.Builder().name(realmName)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()

        realm = Realm.getInstance(config)
    }

    fun addRecipeToShoppingList(recipes: DataRecipeDetail){
        try {
            realm.beginTransaction()
            val rc = RecipeObject()
            rc.id = recipes.id
            rc.name = recipes.name
            rc.result= recipes.result
            rc.image = recipes.image
            rc.ingredients_list.addAll(recipes.ingredients_list)
            realm.copyToRealmOrUpdate(rc)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addNewRecipe(recipes: DataRecipeDetail){
        try {
            realm.beginTransaction()
            val rc = RecipeObject()
            rc.id = System.currentTimeMillis().toInt()
            rc.name = recipes.name
            rc.result= recipes.result
            rc.image = recipes.image
            rc.ingredients_list.addAll(recipes.ingredients_list)
            realm.copyToRealmOrUpdate(rc)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun addIngredienttoShoppingList(recipes: DataRecipeDetail){
//        try {
//            realm.beginTransaction()
//            val rc = RecipeObject()
//            rc.id = recipes.id
//            rc.name = recipes.name
//            realm.copyToRealmOrUpdate(rc)
//            realm.commitTransaction()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun deleteRecipeToShoppingList(id: Int): Boolean {
        realm.beginTransaction()
        val results = realm.where(RecipeObject::class.java).equalTo("id", id).findAll()
        if (results.size > 0) {
            results.deleteFirstFromRealm()
            realm.commitTransaction()
            return true
        }
        realm.commitTransaction()
        return false
    }

    fun getRecipeDB(): ArrayList<DataRecipeDetail> {
        val results = realm.where<RecipeObject>().findAll()
        val recipes = ArrayList<DataRecipeDetail>()
        for (i in results) {
            val rc = DataRecipeDetail()
            rc.id = i.id
            rc.image = i.image
            rc.name = i.name
            rc.ingredients_list.addAll(i.ingredients_list)
            rc.result = i.result
            recipes.add(rc)
        }
        return recipes
    }

}


open class RecipeObject(
    @PrimaryKey
    var id: Int? = 0,
    var name: String? = "",
    var result: String? = "",
    var image: String? = "",
    var ingredients_list: RealmList<Ingredients> = RealmList()
) : RealmObject()

