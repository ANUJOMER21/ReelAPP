package com.Vginfotech.reelapp.API.Repository

import com.Vginfotech.reelapp.API.ApiResult
import com.Vginfotech.reelapp.API.ApiService
import com.Vginfotech.reelapp.API.model.categorymodel
import com.Vginfotech.reelapp.API.model.commonModel
import com.Vginfotech.reelapp.API.model.profilemodel
import com.Vginfotech.reelapp.API.model.reelmodel
import com.Vginfotech.reelapp.API.model.signupmodel
import com.Vginfotech.reelapp.API.safeApiCall
import okhttp3.MultipartBody
import okhttp3.RequestBody
private suspend fun <T> retryOnFailure(
    retries: Int = 1,
    apiCall: suspend () -> ApiResult<T>
): ApiResult<T> {
    repeat(retries - 1) {
        val result = apiCall()
        if (result is ApiResult.Success) return result
    }
    return apiCall() // Final attempt
}
class ApiRepository(private val apiService: ApiService) : APITYPES {

    override suspend fun signup(
        name: String,
        mobile: String,
        email: String,
        password: String
    ): ApiResult<signupmodel> {
        return retryOnFailure{
            safeApiCall { apiService.signup(name,email,mobile,password) }
        }
    }

    override suspend fun login(mobile: String, password: String): ApiResult<signupmodel> {

          return  safeApiCall { apiService.login(mobile,password) }

    }

    override suspend fun verify_otp(mobile: String, otp: String): ApiResult<commonModel> {
        return retryOnFailure {
            safeApiCall { apiService.verify_otp(mobile, otp) }
        }
    }

    override suspend fun send_user_categories(map: HashMap<String, Any>): ApiResult<commonModel> {
        return retryOnFailure {
            safeApiCall { apiService.send_user_categories(map) }
        }
    }

    override suspend fun fetch_user_categories(userid: String): ApiResult<categorymodel> {
        return retryOnFailure {
            safeApiCall { apiService.fetch_user_categories(userid) }
        }
    }

    override suspend fun get_categories(): ApiResult<categorymodel> {
        return retryOnFailure {
            safeApiCall { apiService.get_categories() }
        }
    }

    override suspend fun uploadReel(
        reel_file: MultipartBody.Part,
        caption: RequestBody,
        categoryId: RequestBody,
        userId: RequestBody
    ): ApiResult<commonModel> {
        return retryOnFailure {
            safeApiCall { apiService.uploadReel(reel_file, caption, categoryId, userId) }
        }
    }

    override suspend fun fetch_reels(limit: String, offset: String): ApiResult<reelmodel> {
        return retryOnFailure {
            safeApiCall { apiService.fetch_reels(limit, offset) }
        }
    }

    override suspend fun like_reel(map: HashMap<String, Any>): ApiResult<commonModel> {
        return retryOnFailure {
            safeApiCall { apiService.like_reel(map) }
        }
    }

    override suspend fun updateProfile(
        profile_pic: MultipartBody.Part,
        userId: RequestBody,
        name: RequestBody,
        gender: RequestBody,
        emai: RequestBody,
        categories: RequestBody
    ): ApiResult<commonModel> {
        return retryOnFailure {
            safeApiCall { apiService.updateProfile( userId, name, gender, emai, categories) }
        }
    }

    override suspend fun fetch_profile(userId: String): ApiResult<profilemodel> {
        return retryOnFailure {
            safeApiCall { apiService.fetch_profile(userId) }
        }
    }

    override suspend fun fetch_reel_by_id(reel_id: String): ApiResult<reelmodel> {
        return retryOnFailure {
            safeApiCall { apiService.fetch_reel_by_id(reel_id) }
        }
    }


}