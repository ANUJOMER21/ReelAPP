package com.Vginfotech.reelapp.API.Repository

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.annotation.EmptySuper
import com.Vginfotech.reelapp.API.ApiResult
import com.Vginfotech.reelapp.API.model.categorymodel
import com.Vginfotech.reelapp.API.model.commonModel
import com.Vginfotech.reelapp.API.model.reelmodel
import com.Vginfotech.reelapp.API.model.signupmodel
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface APITYPES {

    suspend fun signup(name:String,mobile:String,email: String,password:String): ApiResult<signupmodel>
    suspend fun login(mobile:String,password:String): ApiResult<signupmodel>
    suspend fun verify_otp(mobile:String,otp:String): ApiResult<commonModel>
    suspend fun send_user_categories(map:HashMap<String, Any>): ApiResult<commonModel>
    suspend fun fetch_user_categories(userid:String): ApiResult<categorymodel>
    suspend fun get_categories(): ApiResult<categorymodel>
    suspend fun uploadReel(reel_file: MultipartBody.Part, caption: RequestBody, categoryId: RequestBody, userId: RequestBody): ApiResult<commonModel>
    suspend fun fetch_reels(limit:String,offset:String): ApiResult<reelmodel>
    suspend fun like_reel(map: HashMap<String, Any>): ApiResult<commonModel>
    suspend fun updateProfile(profile_pic: MultipartBody.Part, userId: RequestBody, name: RequestBody, gender: RequestBody, emai: RequestBody, categories: RequestBody): ApiResult<commonModel>
    suspend fun fetch_profile(userId:String): ApiResult<com.Vginfotech.reelapp.API.model.profilemodel>
    suspend fun fetch_reel_by_id(reel_id:String): ApiResult<reelmodel>


}