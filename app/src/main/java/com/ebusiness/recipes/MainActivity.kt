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

        // Singleton
        val sqlliteHelper = SQLLiteDatabaseHandler(this)

        // Recipes anlegen
        var rec1 = RecipeModel(name="Suppe", text = "Test Text")
        var rec2 = RecipeModel(name="Pizza", text = "Pizza backen")
        var rec3 = RecipeModel(name="Obstsalat", text = "Schnibbeln")

        // Recipes in DB einpflegen
        sqlliteHelper.insertRecipe(rec1)
        sqlliteHelper.insertRecipe(rec2)
        sqlliteHelper.insertRecipe(rec3)

        // Zutaten anlegen
        var ing1 = IngredientModel(name = "Tomate", mmd = "2022", unit = Unit.UNIT)
        var ing2 = IngredientModel(name = "Zucker", mmd = "2022", unit = Unit.GRAM)
        var ing3 = IngredientModel(name = "Butter", mmd =  "2022", unit = Unit.SPOON)

        // Zutaten in DB einpflegen und abfragen
        sqlliteHelper.insertIngredient(ing1)
        sqlliteHelper.insertIngredient(ing2)
        sqlliteHelper.insertIngredient(ing3)

        rec1.addIngredient(ing1, 2, sqlliteHelper)
        rec1.addIngredient(ing2, 4, sqlliteHelper)

        rec2.addIngredient(ing2, 1, sqlliteHelper)
        rec2.addIngredient(ing3, 12, sqlliteHelper)


        rec3.addIngredient(ing1, 3, sqlliteHelper)
        rec3.addIngredient(ing3, 9, sqlliteHelper)

        for(i in sqlliteHelper.getAllRecipes()){
            Log.i("TAG", i.toString())
        }

    }
}