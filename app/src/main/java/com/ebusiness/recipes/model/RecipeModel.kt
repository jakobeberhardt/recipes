package com.ebusiness.recipes.model

import com.ebusiness.recipes.SQLLiteDatabase.SQLLiteDatabaseHandler
import java.util.UUID

data class RecipeModel(
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    var text: String?,
    var ingredients: MutableMap<IngredientModel, Int> = mutableMapOf()
){

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " text:" + this.text +  " ingredients:" + this.ingredients.toString()

    // Fuer alle Funktionen hier: Evt. direkt eine Map als Parameter um staendige sqllite queries zu vermeiden
     fun addIngredient(ing: IngredientModel, amount: Int, db : SQLLiteDatabaseHandler){
        this.ingredients.put(ing, amount)
        db.updateRecipeIngredientList(this)
    }

    fun removeIngredient(ing: IngredientModel, db : SQLLiteDatabaseHandler){
        this.ingredients.remove(ing)
        db.updateRecipeIngredientList(this)
    }

    fun updateIngredientAmount(ing: IngredientModel, amount: Int, db : SQLLiteDatabaseHandler){
        this.ingredients.replace(ing, amount)
        db.updateRecipeIngredientList(this)
    }
}