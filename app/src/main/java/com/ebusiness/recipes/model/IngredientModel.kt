package com.ebusiness.recipes.model
import com.ebusiness.recipes.util.Unit

data class IngredientModel(
    var uuid : String,
    var name : String,
    var mmd : String?,
    var unit : Unit
) {

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " mmd:" + this.mmd + " unit:" + this.unit.toString()

}
