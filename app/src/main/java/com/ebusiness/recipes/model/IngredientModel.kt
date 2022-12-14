package com.ebusiness.recipes.model

import com.ebusiness.recipes.util.Unit
import java.util.UUID

data class IngredientModel(
    val uuid : String = UUID.randomUUID().toString(),
    val name : String,
    var mmd : String?,
    var unit : Unit
) {

    override fun toString(): String = "id:" + this.uuid + " name:" + this.name + " mmd:" + this.mmd + " unit:" + this.unit.toString()

}
