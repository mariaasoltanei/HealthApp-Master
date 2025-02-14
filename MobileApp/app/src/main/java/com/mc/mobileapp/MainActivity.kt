package com.mc.mobileapp

import AppNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mc.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: maybe delete this
        val sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        database = Room.databaseBuilder(
            this.applicationContext,
            AppDatabase::class.java,
            "calaid_app_db"
        ).build()
//        val isDeleted = deleteDatabaseByName("calaid_app_db")
//        if (isDeleted) {
//            println("Database deleted successfully")
//        } else {
//            println("Database deletion failed or database does not exist")
//        }

        val userRepository = UserRepository(database.userDao())

        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory(userRepository)
        }

        setContent {
            MobileAppTheme {
                val navController = rememberNavController()

                AppNavGraph(
                    navController = navController,
                    userViewModel = userViewModel
                )
            }
        }
    }

    private fun deleteDatabaseByName(dbName: String): Boolean {
        return this.applicationContext.deleteDatabase(dbName)
    }
}
