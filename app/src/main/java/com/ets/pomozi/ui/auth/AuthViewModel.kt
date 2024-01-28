package com.ets.pomozi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ets.pomozi.api.Api
import com.ets.pomozi.api.requests.LoginRequest
import com.ets.pomozi.api.requests.RegisterRequest
import com.ets.pomozi.api.responses.GenericResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class AuthViewModel : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login(email: String, password: String) {
        Api.service.login(LoginRequest(email, password)).enqueue(object : Callback<GenericResponse<String>> {
            override fun onResponse(call: Call<GenericResponse<String>>, response: Response<GenericResponse<String>>) {
                when (response.code()) {
                    200 -> {
                        _token.postValue(response.body()!!.data!!)
                    }

                    else -> {
                        val type: Type = object : TypeToken<GenericResponse<String>>() {}.type
                        val body: GenericResponse<String> = Gson().fromJson(response.errorBody()!!.string(), type)

                        _error.postValue(body.error!!)
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse<String>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }

    fun register(name: String, email: String, password: String) {
        Api.service.register(RegisterRequest(name, email, password)).enqueue(object :
            Callback<GenericResponse<String>> {
            override fun onResponse(call: Call<GenericResponse<String>>, response: Response<GenericResponse<String>>) {
                when (response.code()) {
                    200 -> {
                        _token.postValue(response.body()!!.data!!)
                    }

                    else -> {
                        val type: Type = object : TypeToken<GenericResponse<String>>() {}.type
                        val body: GenericResponse<String> = Gson().fromJson(response.errorBody()!!.string(), type)

                        _error.postValue(body.error!!)
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse<String>>, t: Throwable) {
                _error.postValue(t.message)
            }
        })
    }
}
