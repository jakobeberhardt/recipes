package com.ebusiness.recipes.model

data class RecipeModel(
    val uuid: String,
    val name: String,
    // Spaeter Array <IngredientsModel>
    var ingredients: ArrayList<IngredientModel> = ArrayList<IngredientModel>(),
){

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " ingredients:" + this.ingredients.toString()

    fun addIngredient(ing: IngredientModel){
        this.ingredients = this.ingredients ?: ArrayList<IngredientModel>()
            if(this.ingredients.contains(ing).not()){
                this.ingredients.add(ing)
            }
        }

    fun removeIngredient(ing: IngredientModel){
        if(this.ingredients.isEmpty().not()) {
            this.ingredients.remove(ing)
        }
    }
}

