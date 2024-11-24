package com.Vginfotech.reelapp.API

import com.Vginfotech.reelapp.API.model.categorymodel
import com.Vginfotech.reelapp.API.model.commonModel
import com.Vginfotech.reelapp.API.model.profilemodel
import com.Vginfotech.reelapp.API.model.reelmodel
import com.Vginfotech.reelapp.API.model.signupmodel
import com.google.common.base.Objects
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {

    @POST("signup.php")
    @FormUrlEncoded
    suspend fun signup(@Field("name") name: String, @Field("mobile") mobile: String, @Field("email") email:String,@Field("password") password: String):Response<signupmodel>

    @POST("login.php")
    @FormUrlEncoded
    suspend fun login(@Field("mobile") mobile: String, @Field("password") password: String):Response<signupmodel>

    @POST("verify-otp.php")
    @FormUrlEncoded
    suspend fun verify_otp(@Field("mobile") mobile: String, @Field("otp") otp: String):Response<commonModel>

    @GET("categories.php")
    suspend fun get_categories():Response<categorymodel>

    @POST("send_user_categories.php")
    suspend fun send_user_categories(@Body map:HashMap<String, Any>):Response<commonModel>

    @GET("fetch_user_categories.php")
    suspend fun fetch_user_categories(@Query("user_id") userid:String):Response<categorymodel>

    @GET("fetch_reels.php")
    suspend fun fetch_reels(@Query("limit") limit:String,@Query("offset") offset:String):Response<reelmodel>

    @POST("like_reel.php")
    suspend fun like_reel(@Body map: HashMap<String, Any>):Response<commonModel>

    @POST("upload_reels.php")
    @Multipart
    suspend fun uploadReel(
        @Part reel_file: MultipartBody.Part, // For the file upload
        @Part("caption") caption: RequestBody, // For text parts
        @Part("category_id") categoryId: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Response<commonModel> // Or replace with your response model

    @POST("update_profile.php")
    @Multipart
    suspend fun updateProfile(
       // @Part profile_pic: MultipartBody.Part, // For the file upload
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("email") emai: RequestBody,
        @Part("categories") categories: RequestBody,
    ): Response<commonModel>

    @GET("fetch_profile.php")
    suspend fun fetch_profile(@Part("user_id") userId:String):Response<profilemodel>

    @GET("fetch_reel_by_id.php")
    suspend fun fetch_reel_by_id(@Part("reel_id") reel_id:String):Response<reelmodel>
}
val APIURL="https://works.diginspire.in/reel/api/"

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(APIURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().build()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val error: ApiError) : ApiResult<Nothing>()
    }

sealed class ApiError {
    data class NetworkError(val exception: Exception) : ApiError()
    data class HttpError(val code: Int, val message: String) : ApiError()
    data class UnexpectedError(val exception: Exception) : ApiError()
}





suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {

            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Failure(ApiError.UnexpectedError(Exception("Response body is null")))
                }
            } else {
                ApiResult.Failure(ApiError.HttpError(response.code(), response.message()))
            }
        } catch (e: IOException) { // Network-related exceptions
            ApiResult.Failure(ApiError.NetworkError(e))
        } catch (e: Exception) { // Other unexpected exceptions
            ApiResult.Failure(ApiError.UnexpectedError(e))
        }
    }
}
