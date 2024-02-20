package com.ets.pomozi.api

import com.ets.pomozi.api.requests.EditUserRequest
import com.ets.pomozi.api.requests.LoginRequest
import com.ets.pomozi.api.requests.RegisterRequest
import com.ets.pomozi.api.responses.GenericResponse
import com.ets.pomozi.api.responses.Last2DonationsResponse
import com.ets.pomozi.models.ActionModel
import com.ets.pomozi.models.DonationModel
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.models.RewardModel
import com.ets.pomozi.models.UserModel
import com.ets.pomozi.models.UserRewardModel
import com.ets.pomozi.util.GlobalData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object Api {
    private val retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val headersInterceptor = Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Accept", "application/json")
            chain.proceed(requestBuilder.build())
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(GlobalData.API_PREFIX)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: ApiInterface = retrofit.create()
}

interface ApiInterface {
    @POST("auth/login")
    fun login(@Body body: LoginRequest): Call<GenericResponse<String>>

    @POST("auth/register")
    fun register(@Body body: RegisterRequest): Call<GenericResponse<String>>

    @GET("user")
    fun user(@Header("Authorization") token: String): Call<GenericResponse<UserModel>>

    @PUT("user/edit")
    fun editUser(@Header("Authorization") token: String, @Body body: EditUserRequest): Call<GenericResponse<UserModel>>

    @GET("user/donations")
    fun donations(@Header("Authorization") token: String): Call<GenericResponse<ArrayList<DonationModel>>>

    @GET("reward")
    fun rewards(@Query("query") query: String): Call<GenericResponse<ArrayList<RewardModel>>>

    @GET("reward/user")
    fun userRewards(@Header("Authorization") token: String): Call<GenericResponse<ArrayList<UserRewardModel>>>

    @POST("reward/buy/{id}")
    fun buyReward(@Header("Authorization") token: String, @Path("id") rewardId: Int): Call<GenericResponse<Unit?>>

    @GET("organization")
    fun organizations(@Query("query") query: String): Call<GenericResponse<ArrayList<OrganizationModel>>>

    @GET("stats/leaderboard")
    fun leaderboard(): Call<GenericResponse<ArrayList<UserModel>>>

    @GET("stats/top-donation")
    fun topDonation(): Call<GenericResponse<DonationModel?>>

    @GET("stats/last-2-donations")
    fun last2Donations(): Call<GenericResponse<Last2DonationsResponse>>

    @GET("action")
    fun actions(): Call<GenericResponse<ArrayList<ActionModel>>>
}
