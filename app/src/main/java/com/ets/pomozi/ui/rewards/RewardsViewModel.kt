package com.ets.pomozi.ui.rewards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.pomozi.api.Api
import com.ets.pomozi.api.responses.GenericResponse
import com.ets.pomozi.models.RewardModel
import com.ets.pomozi.util.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardsViewModel : ViewModel() {
    private val _rewards = MutableLiveData<ArrayList<RewardModel>>()
    val rewards: LiveData<ArrayList<RewardModel>> = _rewards

    private val _bought = MutableLiveData(false)
    val bought: LiveData<Boolean> = _bought

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadRewards(query: String) {
        Api.service.rewards(query).enqueue(object :
            Callback<GenericResponse<ArrayList<RewardModel>>> {
            override fun onResponse(call: Call<GenericResponse<ArrayList<RewardModel>>>, response: Response<GenericResponse<ArrayList<RewardModel>>>) {
                if (response.code() == 200) {
                    _rewards.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<RewardModel>>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun buyReward(rewardId: Int) {
        val token = "Bearer ${GlobalData.getToken()}"
        Api.service.buyReward(token, rewardId).enqueue(object : Callback<GenericResponse<Unit?>> {
            override fun onResponse(call: Call<GenericResponse<Unit?>>, response: Response<GenericResponse<Unit?>>) {
                if (response.code() == 200) {
                    _bought.postValue(true)
                }
            }

            override fun onFailure(call: Call<GenericResponse<Unit?>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }
}