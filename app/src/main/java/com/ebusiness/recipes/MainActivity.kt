package com.ebusiness.recipes

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ebusiness.recipes.databinding.ActivityMainBinding
import android.util.Log
import com.ebusiness.recipes.SQLLiteDatabase.SQLLiteDatabaseHandler
import com.ebusiness.recipes.model.IngredientModel
import com.ebusiness.recipes.model.RecipeModel
import com.ebusiness.recipes.util.Unit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Debugging und Testobjekte

        Log.i("TAG", "App started")

        val sqlliteHelper = SQLLiteDatabaseHandler(this)


        // Recipes
        val rec1 = RecipeModel("001","Suppe")
        val rec2 = RecipeModel("002","Pizza")
        val rec3 = RecipeModel("003","Obstsalat")

//        sqlliteHelper.insertRecipe(rec1)
//        sqlliteHelper.insertRecipe(rec2)
//        sqlliteHelper.insertRecipe(rec3)

        val list_rec = sqlliteHelper.getAllRecipes()
        Log.i("TAG", "Recipes:")
        for(i in list_rec){ Log.i("TAG",i.toString()) }

        // Ingredients
        val ing1 = IngredientModel("004", "Tomate", "01062023", Unit.UNIT)
        val ing2 = IngredientModel("005", "Apfel", "01012023", Unit.UNIT)
        val ing3 = IngredientModel("006", "Zucker", "", Unit.SPOON)
        val ing4 = IngredientModel("007", "Kartoffel", "02012023", Unit.GRAM)

//        sqlliteHelper.insertIngredient(ing1)
//        sqlliteHelper.insertIngredient(ing2)
//        sqlliteHelper.insertIngredient(ing3)
//        sqlliteHelper.insertIngredient(ing4)

        val list_ing = sqlliteHelper.getAllIngredients()
        for(i in list_ing){ Log.i("TAG",i.toString()) }

        val ing_getby = sqlliteHelper.getIngredientByName("Apfel")
        Log.i("TAG",ing_getby.toString())

        val rec_getby = sqlliteHelper.getRecipeByName("Suppe")
        Log.i("TAG",rec_getby.toString())



        Log.i("TAG",rec1.toString())
        Log.i("TAG",ing1.toString())

        // Add
        rec1.addIngredient(ing1)
        rec1.addIngredient(ing2)
        rec1.addIngredient(ing3)
        rec1.addIngredient(ing4)
        // Doppelt
        rec1.addIngredient(ing4)
        Log.i("TAG",rec1.toString())

        // Test Remove



    }
}