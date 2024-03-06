package com.example.newspulse.ui.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newspulse.R
import com.example.newspulse.databinding.ActivityMainBinding
import com.example.newspulse.ui.fragments.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        setupNavigation()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun setupNavigation() {
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding?.bottomNavigationView?.visibility = when (destination.id) {
                R.id.newsFragment, R.id.webViewFragment -> View.VISIBLE
                else -> View.GONE
            }
        }

        binding?.let {
            bottomNavigationView = it.bottomNavigationView
            NavigationUI.setupWithNavController(bottomNavigationView, navController)
        }
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.fragments.first()
        if (currentFragment is NewsFragment) {
            showExitConfirmationDialog()
            } else {
            super.onBackPressed()
            }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.exit_confirmation))
        builder.setMessage(getString(R.string.message_exit))
        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}