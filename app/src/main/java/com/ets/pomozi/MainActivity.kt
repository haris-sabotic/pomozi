package com.ets.pomozi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ets.pomozi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNavScreen = "Home";
    private var navController: NavController? = null
    private var navView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.activity_main_fragment)
        navView = binding.activityMainNav

        // do this for the splash screen
        navView?.visibility = View.GONE

        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_splash,
                R.id.navigation_slider1, R.id.navigation_slider2, R.id.navigation_slider3,
                R.id.navigation_login, R.id.navigation_register,
                R.id.navigation_edit_profile -> {
                    navView?.visibility = View.GONE
                }

                else -> {
                    navView?.visibility = View.VISIBLE
                }
            }
        }

        binding.activityMainNavHomeOverlay.setOnClickListener { navigateTo("Home") }
        binding.activityMainNavRewardsOverlay.setOnClickListener { navigateTo("Rewards") }
        binding.activityMainNavMapOverlay.setOnClickListener { navigateTo("Map") }
        binding.activityMainNavProfileOverlay.setOnClickListener { navigateTo("Profile") }
    }

    private fun navigateTo(screen: String) {
        when (currentNavScreen) {
            "Home" -> when (screen) {
                "Rewards" -> navController?.navigate(R.id.action_home_to_rewards)
                "Map" -> navController?.navigate(R.id.action_home_to_map)
                "Profile" -> navController?.navigate(R.id.action_home_to_profile)
            }
            "Rewards" -> when (screen) {
                "Home" -> navController?.navigate(R.id.action_rewards_to_home)
                "Map" -> navController?.navigate(R.id.action_rewards_to_map)
                "Profile" -> navController?.navigate(R.id.action_rewards_to_profile)
            }
            "Map" -> when (screen) {
                "Home" -> navController?.navigate(R.id.action_map_to_home)
                "Rewards" -> navController?.navigate(R.id.action_map_to_rewards)
                "Profile" -> navController?.navigate(R.id.action_map_to_profile)
            }
            "Profile" -> when (screen) {
                "Home" -> navController?.navigate(R.id.action_profile_to_home)
                "Rewards" -> navController?.navigate(R.id.action_profile_to_rewards)
                "Map" -> navController?.navigate(R.id.action_profile_to_map)
            }
        }

        binding.activityMainNavHome.imageTintList = ContextCompat.getColorStateList(this, R.color.lightgray_on_cool_blue_gray)
        binding.activityMainNavRewards.imageTintList = ContextCompat.getColorStateList(this, R.color.lightgray_on_cool_blue_gray)
        binding.activityMainNavMap.imageTintList = ContextCompat.getColorStateList(this, R.color.lightgray_on_cool_blue_gray)
        binding.activityMainNavProfile.imageTintList = ContextCompat.getColorStateList(this, R.color.lightgray_on_cool_blue_gray)
        binding.activityMainNavHomeActiveUnderline.visibility = View.INVISIBLE
        binding.activityMainNavRewardsActiveUnderline.visibility = View.INVISIBLE
        binding.activityMainNavMapActiveUnderline.visibility = View.INVISIBLE
        binding.activityMainNavProfileActiveUnderline.visibility = View.INVISIBLE

        when (screen) {
            "Home" -> {
                binding.activityMainNavHome.imageTintList = ContextCompat.getColorStateList(this, R.color.red_on_cool_blue_gray)
                binding.activityMainNavHomeActiveUnderline.visibility = View.VISIBLE
            }
            "Rewards" -> {
                binding.activityMainNavRewards.imageTintList = ContextCompat.getColorStateList(this, R.color.red_on_cool_blue_gray)
                binding.activityMainNavRewardsActiveUnderline.visibility = View.VISIBLE
            }
            "Map" -> {
                binding.activityMainNavMap.imageTintList = ContextCompat.getColorStateList(this, R.color.red_on_cool_blue_gray)
                binding.activityMainNavMapActiveUnderline.visibility = View.VISIBLE
            }
            "Profile" -> {
                binding.activityMainNavProfile.imageTintList = ContextCompat.getColorStateList(this, R.color.red_on_cool_blue_gray)
                binding.activityMainNavProfileActiveUnderline.visibility = View.VISIBLE
            }
        }

        currentNavScreen = screen
    }
}