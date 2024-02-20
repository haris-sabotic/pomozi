package com.ets.pomozi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.pomozi.api.Api
import com.ets.pomozi.api.responses.GenericResponse
import com.ets.pomozi.api.responses.Last2DonationsResponse
import com.ets.pomozi.models.ActionModel
import com.ets.pomozi.models.DonationModel
import com.ets.pomozi.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _topDonation = MutableLiveData<DonationModel?>()
    val topDonation: LiveData<DonationModel?> = _topDonation

    private val _last2Donations = MutableLiveData<Last2DonationsResponse>()
    val last2Donations: LiveData<Last2DonationsResponse> = _last2Donations

    private val _leaderboard = MutableLiveData<ArrayList<UserModel>>()
    val leaderboard: LiveData<ArrayList<UserModel>> = _leaderboard

    private val _actions = MutableLiveData<ArrayList<ActionModel>>()
    val actions: LiveData<ArrayList<ActionModel>> = _actions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadTopDonation() {
        Api.service.topDonation().enqueue(object : Callback<GenericResponse<DonationModel?>> {
            override fun onResponse(
                call: Call<GenericResponse<DonationModel?>>,
                response: Response<GenericResponse<DonationModel?>>
            ) {
                if (response.isSuccessful) {
                    _topDonation.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<GenericResponse<DonationModel?>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun loadLast2Donations() {
        Api.service.last2Donations().enqueue(object : Callback<GenericResponse<Last2DonationsResponse>> {
            override fun onResponse(
                call: Call<GenericResponse<Last2DonationsResponse>>,
                response: Response<GenericResponse<Last2DonationsResponse>>
            ) {
                if (response.isSuccessful) {
                    _last2Donations.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<Last2DonationsResponse>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }


    fun loadLeaderboard() {
        Api.service.leaderboard().enqueue(object : Callback<GenericResponse<ArrayList<UserModel>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<UserModel>>>,
                response: Response<GenericResponse<ArrayList<UserModel>>>
            ) {
                if (response.isSuccessful) {
                    _leaderboard.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<UserModel>>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun loadActions() {
        Api.service.actions().enqueue(object :
            Callback<GenericResponse<ArrayList<ActionModel>>> {
            override fun onResponse(call: Call<GenericResponse<ArrayList<ActionModel>>>, response: Response<GenericResponse<ArrayList<ActionModel>>>) {
                if (response.code() == 200) {
                    _actions.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<ActionModel>>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }
}