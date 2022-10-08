package com.ebusiness.recipes.model

data class IngredientModel(
    var uuid : String,
    var name : String,
    var mmd : String?
) {

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " mmd:" + this.mmd

}
