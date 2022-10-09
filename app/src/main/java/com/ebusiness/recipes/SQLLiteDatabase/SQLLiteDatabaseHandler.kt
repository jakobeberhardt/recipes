package com.ebusiness.recipes.SQLLiteDatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ebusiness.recipes.model.RecipeModel
import android.util.Log
import com.ebusiness.recipes.model.IngredientModel
import com.ebusiness.recipes.util.Unit

class SQLLiteDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {

        // Db
        private const val DATABASE_VERSION = 15
        private const val DATABASE_NAME = "db0"

        // Rezept Tabelle
        private const val TABLE_NAME_RECIPE = "recipes"
        private const val RECIPE_ID = "recipe_uuid"
        private const val RECIPE_NAME = "recipe_name"
        private const val INGREDIENT_LIST_FK = "ingredient_list_fk"

        // Zutatenliste Tabelle
        private const val TABLE_NAME_INGREDIENT_LIST = "ingredient_lists"
        private const val INGREDIENT_LIST_ID = "ingredient_list_uuid"

        // Zutat Tabelle
        private const val TABLE_NAME_INGREDIENT = "ingredients"
        private const val INGREDIENT_ID = "ingredient_uuid"
        private const val INGREDIENT_NAME = "ingredient_name"
        private const val INGREDIENT_MMD = "ingredient_mmd"
        private const val INGREDIENT_UNIT = "ingredient_unit"
    }

    override fun onCreate(db0: SQLiteDatabase?) {
        Log.i("TAG", "Attempting to create DB")
        val createRecipeTable = ("CREATE TABLE " + TABLE_NAME_RECIPE + "("
                + RECIPE_ID + " TEXT PRIMARY KEY, "
                + RECIPE_NAME + " TEXT, "
                + INGREDIENT_LIST_FK + " INTEGER" + ")")
        db0?.execSQL(createRecipeTable)
        Log.i("TAG", "Created $TABLE_NAME_RECIPE Table")

        val createIngredientTable = ("CREATE TABLE " + TABLE_NAME_INGREDIENT + "("
                + INGREDIENT_ID + " TEXT PRIMARY KEY, "
                + INGREDIENT_NAME + " TEXT, "
                + INGREDIENT_MMD + " TEXT, "
                + INGREDIENT_UNIT + " TEXT" + ")")
        db0?.execSQL(createIngredientTable)
        Log.i("TAG", "Created $TABLE_NAME_INGREDIENT Table")
    }

    override fun onUpgrade(db0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.i("TAG", "Upgrading DB")
        db0!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_RECIPE")
        db0.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_INGREDIENT")
        Log.i("TAG", "Upgraded DB")
        onCreate(db0)
    }

    fun insertRecipe(rec: RecipeModel): Long{
        val db0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(RECIPE_ID, rec.uuid)
        contentValues.put(RECIPE_NAME, rec.name)
        contentValues.put(INGREDIENT_LIST_FK, rec.ingredients)

        val success = db0.insert(TABLE_NAME_RECIPE, null,  contentValues)
        db0.close()
        Log.i("TAG", "Inserted new Recipe with ID " + rec.uuid)
        return success
    }

    fun getAllRecipes(): ArrayList<RecipeModel>{
        val recipeList: ArrayList<RecipeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME_RECIPE"
        val db0 = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db0.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db0.execSQL(selectQuery)
            return ArrayList()
        }

        var uuid: String
        var name: String
        var ingredients : Int

        if(cursor.moveToFirst()) {
            do {
                uuid = cursor.getString(cursor.getColumnIndex(RECIPE_ID))
                name = cursor.getString(cursor.getColumnIndex(RECIPE_NAME))
                ingredients = cursor.getInt(cursor.getColumnIndex(INGREDIENT_LIST_FK))

                val rec = RecipeModel(uuid = uuid, name = name, ingredients = ingredients)
                recipeList.add(rec)
            } while (cursor.moveToNext())
        }

        return recipeList
    }

    fun getRecipeByUuid(_uuid: String): RecipeModel? {
        val db0 = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME_RECIPE WHERE $RECIPE_ID = ?"
        db0.rawQuery(selectQuery, arrayOf(_uuid)).use {
            if (it.moveToFirst()) {
                var uuid = it.getString(it.getColumnIndex(RECIPE_ID))
                var name = it.getString(it.getColumnIndex(RECIPE_NAME))
                var ingredients = it.getInt(it.getColumnIndex(INGREDIENT_LIST_FK))
                return RecipeModel(uuid = uuid, name = name, ingredients = ingredients)
            }
        }
        return null
    }

    fun getRecipeByName(_name: String): RecipeModel? {
        val db0 = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME_RECIPE WHERE $RECIPE_NAME = ?"
        db0.rawQuery(selectQuery, arrayOf(_name)).use {
            if (it.moveToFirst()) {
                var uuid = it.getString(it.getColumnIndex(RECIPE_ID))
                var name = it.getString(it.getColumnIndex(RECIPE_NAME))
                var ingredients = it.getInt(it.getColumnIndex(INGREDIENT_LIST_FK))
                return RecipeModel(uuid = uuid, name = name, ingredients = ingredients)
            }
        }
        return null
    }

    fun insertIngredient(ing: IngredientModel): Long{
        val db0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(INGREDIENT_ID, ing.uuid)
        contentValues.put(INGREDIENT_NAME, ing.name)
        contentValues.put(INGREDIENT_MMD, ing.mmd)
        contentValues.put(INGREDIENT_UNIT, ing.unit.toString())

        val success = db0.insert(TABLE_NAME_INGREDIENT, null,  contentValues)
        db0.close()
        Log.i("TAG", "Inserted new Ingredient with ID " + ing.uuid)
        return success
    }

    fun getAllIngredients(): ArrayList<IngredientModel>{
        val ingredientList: ArrayList<IngredientModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME_INGREDIENT"
        val db0 = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db0.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db0.execSQL(selectQuery)
            return ArrayList()
        }

        var uuid: String
        var name: String
        var mmd : String
        var unit : String

        if(cursor.moveToFirst()) {
            do {
                uuid = cursor.getString(cursor.getColumnIndex(INGREDIENT_ID))
                name = cursor.getString(cursor.getColumnIndex(INGREDIENT_NAME))
                mmd = cursor.getString(cursor.getColumnIndex(INGREDIENT_MMD))
                unit = cursor.getString(cursor.getColumnIndex(INGREDIENT_UNIT))

                val ing = IngredientModel(uuid = uuid, name = name, mmd = mmd, unit = Unit.valueOf(unit))
                ingredientList.add(ing)
            } while (cursor.moveToNext())
        }

        return ingredientList
    }

    fun getIngredientByUuid(_uuid: String): IngredientModel? {
            val db0 = this.writableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME_INGREDIENT WHERE $INGREDIENT_ID = ?"
            db0.rawQuery(selectQuery, arrayOf(_uuid)).use {
                if (it.moveToFirst()) {
                    var uuid = it.getString(it.getColumnIndex(INGREDIENT_ID))
                    var name = it.getString(it.getColumnIndex(INGREDIENT_NAME))
                    var mmd = it.getString(it.getColumnIndex(INGREDIENT_MMD))
                    var unit = it.getString(it.getColumnIndex(INGREDIENT_UNIT))
                    return IngredientModel(uuid = uuid, name = name, mmd = mmd, unit = Unit.valueOf(unit))
                }
            }
            return null
        }

    fun getIngredientByName(_name: String): IngredientModel? {
        val db0 = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME_INGREDIENT WHERE $INGREDIENT_NAME = ?"
        db0.rawQuery(selectQuery, arrayOf(_name)).use {
            if (it.moveToFirst()) {
                var uuid = it.getString(it.getColumnIndex(INGREDIENT_ID))
                var name = it.getString(it.getColumnIndex(INGREDIENT_NAME))
                var mmd = it.getString(it.getColumnIndex(INGREDIENT_MMD))
                var unit = it.getString(it.getColumnIndex(INGREDIENT_UNIT))
                return IngredientModel(uuid = uuid, name = name, mmd = mmd, unit = Unit.valueOf(unit))
            }
        }
        return null
    }

    }