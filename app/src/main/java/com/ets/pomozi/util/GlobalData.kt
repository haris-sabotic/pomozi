package com.ets.pomozi.util

import android.content.Context
import com.ets.pomozi.R
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.models.RewardModel

object GlobalData {
    private var _token: String? = null

    fun loadTokenFromSharedPrefs(content: Context) {
        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        _token = sharedPrefs.getString("token", null)
    }

    fun saveToken(content: Context, value: String?) {
        _token = value

        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        if (value == null) {
            editor.remove("token")
        } else {
            editor.putString("token", value)
        }
        editor.apply()
    }

    fun setToken(value: String?) {
        _token = value
    }

    fun getToken(): String? {
        return _token
    }

    val API_PREFIX = "http://84.247.177.105:3001/api/"

    val REWARDS = arrayListOf<RewardModel>(
        RewardModel("Cineplexx popust", "50% popusta na filmove", 400, "http://84.247.177.105/storage/cineplexx.jpg"),
        RewardModel("Zara popust", "30% popusta na sve artikle", 550, "http://84.247.177.105/storage/zara.jpg"),
        RewardModel("Burger King popust", "10% popusta na sve burgere", 300, "http://84.247.177.105/storage/burger_king.jpg"),
        RewardModel("Goodfellas popust", "45% popusta na sve pizze", 300, "http://84.247.177.105/storage/goodfellas.jpg"),
    )

    val ORGANIZATIONS = arrayListOf<OrganizationModel>(
        OrganizationModel("Crveni krst", R.drawable.home_card_2, "https://ckcg.me/", 42.44341820074499, 19.256125218420586),
        OrganizationModel("Budi human", R.drawable.home_card_3, "http://www.budihuman.rs/", 44.81270747387252, 20.411759167639676),
        OrganizationModel("Humanost", R.drawable.home_card_1, "https://fondacija.rs/", 44.78532816871793, 20.46316491149266)
    )
}
