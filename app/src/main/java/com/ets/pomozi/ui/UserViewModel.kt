package com.ets.pomozi.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.pomozi.api.Api
import com.ets.pomozi.api.requests.EditUserRequest
import com.ets.pomozi.api.responses.GenericResponse
import com.ets.pomozi.models.DonationModel
import com.ets.pomozi.models.RewardModel
import com.ets.pomozi.models.UserModel
import com.ets.pomozi.models.UserRewardModel
import com.ets.pomozi.util.GlobalData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class UserViewModel : ViewModel() {
    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> = _userData

    private val _donations = MutableLiveData<ArrayList<DonationModel>>()
    val donations: LiveData<ArrayList<DonationModel>> = _donations

    private val _rewards = MutableLiveData<ArrayList<UserRewardModel>>()
    val rewards: LiveData<ArrayList<UserRewardModel>> = _rewards

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadUserData() {
        val token = "Bearer ${GlobalData.getToken()}"
        Api.service.user(token).enqueue(object :
            Callback<GenericResponse<UserModel>> {
            override fun onResponse(call: Call<GenericResponse<UserModel>>, response: Response<GenericResponse<UserModel>>) {
                if (response.code() == 200) {
                    _userData.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<UserModel>>, t: Throwable) {
                _error.postValue(t.message)
                Log.d("USERVIEWMODEL_USER_DATA", t.message.toString())
            }
        })
    }

    fun loadDonations() {
        val token = "Bearer ${GlobalData.getToken()}"
        Api.service.donations(token).enqueue(object :
            Callback<GenericResponse<ArrayList<DonationModel>>> {
            override fun onResponse(call: Call<GenericResponse<ArrayList<DonationModel>>>, response: Response<GenericResponse<ArrayList<DonationModel>>>) {
                if (response.code() == 200) {
                    _donations.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<DonationModel>>>, t: Throwable) {
                _error.postValue(t.message)
                Log.d("USERVIEWMODEL_DONATIONS", t.message.toString())
            }
        })
    }

    fun loadRewards() {
        val token = "Bearer ${GlobalData.getToken()}"
        Api.service.userRewards(token).enqueue(object :
            Callback<GenericResponse<ArrayList<UserRewardModel>>> {
            override fun onResponse(call: Call<GenericResponse<ArrayList<UserRewardModel>>>, response: Response<GenericResponse<ArrayList<UserRewardModel>>>) {
                if (response.code() == 200) {
                    _rewards.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<UserRewardModel>>>, t: Throwable) {
                _error.postValue(t.message)
                Log.d("USERVIEWMODEL_REWARDS", t.message.toString())
            }
        })
    }

    fun editUser(data: EditUserRequest) {
        val token = "Bearer ${GlobalData.getToken()}"
        Api.service.editUser(token, data).enqueue(object :
            Callback<GenericResponse<UserModel>> {
            override fun onResponse(
                call: Call<GenericResponse<UserModel>>,
                response: Response<GenericResponse<UserModel>>
            ) {
                when (response.code()) {
                    200 -> {
                        _userData.postValue(response.body()!!.data!!)
                    }

                    else -> {
                        val type: Type = object : TypeToken<GenericResponse<String>>() {}.type
                        val body: GenericResponse<String> = Gson().fromJson(response.errorBody()!!.string(), type)

                        _error.postValue(body.error!!)
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse<UserModel>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })

    }
}
