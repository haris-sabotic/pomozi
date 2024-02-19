package com.ets.pomozi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.pomozi.api.Api
import com.ets.pomozi.api.responses.GenericResponse
import com.ets.pomozi.models.OrganizationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizationViewModel : ViewModel() {
    private val _organizations = MutableLiveData<ArrayList<OrganizationModel>>()
    val organizations: LiveData<ArrayList<OrganizationModel>> = _organizations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadOrganizations(query: String) {
        Api.service.organizations(query).enqueue(object :
            Callback<GenericResponse<ArrayList<OrganizationModel>>> {
            override fun onResponse(call: Call<GenericResponse<ArrayList<OrganizationModel>>>, response: Response<GenericResponse<ArrayList<OrganizationModel>>>) {
                if (response.code() == 200) {
                    _organizations.postValue(response.body()!!.data!!)
                }
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<OrganizationModel>>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }
}