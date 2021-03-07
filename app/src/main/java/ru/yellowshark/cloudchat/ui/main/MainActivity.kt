package ru.yellowshark.cloudchat.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ru.yellowshark.cloudchat.R
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.databinding.ActivityMainBinding
import ru.yellowshark.cloudchat.utils.restartActivity
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //initNavController()
    }
    /*private fun initNavController() {
        with(binding) {
            setSupportActionBar(toolbar)
            val navController = findNavController(R.id.nav_host_fragment)
            NavigationUI.setupActionBarWithNavController(this@MainActivity, navController, drawerLayout)
            val appBarConfig = AppBarConfiguration(navController.graph, drawerLayout, fallbackOnNavigateUpListener = {
                true
            })
            navigationView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfig)
        }
    }*/


}