package com.ebusiness.recipes.model

data class RecipeModel(
    var uuid: String,
    var name: String,
    // Spaeter Array <IngredientsModel>
    var ingredients: Int?,
){

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " ingredients:" + this.ingredients.toString()
}
