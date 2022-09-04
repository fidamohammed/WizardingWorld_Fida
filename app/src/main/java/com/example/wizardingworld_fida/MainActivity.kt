package com.example.wizardingworld_fida

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wizardingworld_fida.databinding.ActivityMainBinding
import com.example.wizardingworld_fida.ui.characterList.CharacterListViewModel
import com.example.wizardingworld_fida.ui.signIn.SignInActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(currentUser==null){
            startActivity(Intent(this,SignInActivity::class.java))
        }
        else{
            val loggedinUser = currentUser
            Log.d("LoggedInUser","$loggedinUser ")
            val drawer: DrawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navView

            val headerView = navView.getHeaderView(0)

            val userId = headerView.findViewById<TextView>(R.id.loggedInUserId)
            val userName = headerView.findViewById<TextView>(R.id.loggedInUserName)
//            userName.text = loggedinUser.uid
            userId.text = loggedinUser.email

            val navController = findNavController(R.id.navHostFragment)

            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.characterListFragment, R.id.favouritesFragment
            ), drawer)

            navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
                mAuth.signOut()
                startActivity(Intent(this,SignInActivity::class.java))
                true
            }
            setupActionBarWithNavController(navController,appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController,appBarConfiguration) || super.onSupportNavigateUp()
    }

}