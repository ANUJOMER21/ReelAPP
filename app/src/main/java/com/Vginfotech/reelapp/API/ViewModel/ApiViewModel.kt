package com.Vginfotech.reelapp.API.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Vginfotech.reelapp.API.ApiResult
import com.Vginfotech.reelapp.API.Repository.ApiRepository
import com.Vginfotech.reelapp.API.model.Category
import com.Vginfotech.reelapp.API.model.categorymodel
import com.Vginfotech.reelapp.API.model.commonModel
import com.Vginfotech.reelapp.API.model.profilemodel
import com.Vginfotech.reelapp.API.model.reelmodel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.Vginfotech.reelapp.Navigation.Navigation
import com.Vginfotech.reelapp.SharedPrefManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File


class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel(), KoinComponent {

    private val sharedPrefManager: SharedPrefManager by inject()
    private fun String.isSuccess(): Boolean= this == "success"

    private fun saveNumber(number: String) =sharedPrefManager.saveNumber(number.toString())
    private fun saveUserId(user_id:String) =sharedPrefManager.saveUserId(user_id)
    private fun getNumber(): String? =sharedPrefManager.getNumber()
    private fun getUserId(): String? =sharedPrefManager.getUserId()

    // Login/Signup data and error states
    private val _NavigationResult = MutableStateFlow<Navigation?>(null)
    val NavigationResult:StateFlow<Navigation?> = _NavigationResult


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _Error = MutableStateFlow<String?>(null)
    val Error: StateFlow<String?> = _Error

    fun setNavigationNull(){
        _NavigationResult.value=null
    }
    private val _message =MutableStateFlow<String>("")
    val message :StateFlow<String> =_message
    // Categories data and error states
    private val _categoriesData = MutableStateFlow<categorymodel?>(null)
    val categoriesData: StateFlow<categorymodel?> = _categoriesData
    private val _selectedcategoriesData = MutableStateFlow<categorymodel?>(null)
    val selectedcategoriesData: StateFlow<categorymodel?> = _selectedcategoriesData

    // Loading state for general feedback
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signup(name: String, mobile: String, email: String,gender: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = apiRepository.signup(name, mobile, email, password)) {
                is ApiResult.Success -> {
                    val data = result.data
                    if(data.status.isSuccess()) {
                        saveNumber(mobile)
                        _NavigationResult.value = Navigation.OtpPage
                    }
                    else{
                        _Error.value=data.message
                    }

                    Log.d("OTP FROM API",data.otp.toString())
                }
                is ApiResult.Failure -> {
                   _Error.value=result.error.toString()
                }


            }
            _isLoading.value = false
        }
    }

    fun login(mobile: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = apiRepository.login(mobile, password)
                when (result) {
                    is ApiResult.Success -> {

                        val data = result.data
                if(data.status.isSuccess()){
                    saveNumber(mobile)
                    _NavigationResult.value = Navigation.OtpPage
                }
                else{
                            _Error.value=data.message

                }
                        Log.d("OTP FROM API",data.otp.toString())

                    }

                    is ApiResult.Failure -> {
                        _Error.value = result.error.toString()
                    }
                }
            } catch (e: Exception) {
                _Error.value=e.toString()
                Log.d("Error Login",e.toString())
            } finally {
                _loading.value = false
            }
        }
    }

    fun verify_otp( otp: String){
        viewModelScope.launch {
            _loading.value = true
            val mobile=getNumber()
            Log.d("mobileNumber",mobile.toString())
            try {
                val result=apiRepository.verify_otp(mobile = mobile!!,otp= otp )
                when(result){
                    is ApiResult.Failure ->{
                        _Error.value = result.error.toString()
                    }
                    is ApiResult.Success ->{
                        val data = result.data
                        if(data.status.isSuccess()){
                            saveUserId(data.user_id.toString())
                            _NavigationResult.value = Navigation.CategorySelector
                        }
                        else{
                            _Error.value=data.message
                        }

                    }
                }
            }
            catch (e:Exception){
                _Error.value=e.toString()
                Log.d("Error OtpVerify",e.toString())
            }
            finally {
                _loading.value =false
            }
        }
    }


    fun getCategories()  {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = apiRepository.get_categories()) {
                is ApiResult.Success -> {
                    val data=result.data
                    if(data.status.isSuccess())
                    {     val modify_category=data.categories.map {
                        it.copy(category_id = it.id, category_name = it.name)
                    }
                        val newdata=categorymodel(
                            status = data.status,
                            categories = modify_category
                        )
                        Log.d("getCategories data",newdata.toString())
                        _categoriesData.value = newdata


                    }else{
                        _Error.value="No Category"
                    }

                }
                is ApiResult.Failure -> {

                  _Error.value = result.error.toString()
                }


            }
            _isLoading.value = false
        }
    }

    fun submitcategory( category:List<Category>){
        _loading.value = true
        val userId=getUserId()
        val list=category.map { it.id }
        val map=HashMap<String,Any>()
        map.put("user_id",userId.toString())
        map.put("categories",list)
        viewModelScope.launch {
            try {
                val result=apiRepository.send_user_categories(map)
                when(result){
                    is ApiResult.Failure ->{
                        _Error.value = result.error.toString()
                    }
                    is ApiResult.Success ->{
                        val data = result.data
                        if(data.status.isSuccess()){
                            _NavigationResult.value = Navigation.Reels
                        }
                        else{
                            _Error.value=data.message
                        }
                    }

                }

            }
            catch (e:Exception){
                _Error.value =e.toString()
                Log.d("Error_submit_category",e.toString())
            }

        }



    }

    fun fetchusercategories(){
        val userId=getUserId()
        Log.d("user_id",userId.toString())


        viewModelScope.launch {
            _isLoading.value = true

            try {
                val user_category_result=apiRepository.fetch_user_categories(userId.toString())
                when(user_category_result){
                    is ApiResult.Failure ->{
                        _Error.value = user_category_result.error.toString()
                    }
                    is ApiResult.Success->{
                        val data=user_category_result.data

                        if(data.status.isSuccess()){

                             val modify_category=data.categories.map {
                                 it.copy(id = it.category_id,name = it.category_name)
                             }
                            val newdata=categorymodel(
                                status = data.status,
                                categories = modify_category
                            )
                            Log.d("fetchusercategories data",newdata.toString())
                            _selectedcategoriesData.value=newdata
                        }
                        else{
                            _Error.value=data.status
                        }
                    }
                }
            }
            catch (e:Exception){

                Log.d("Exception",e.toString())
            }
        }
    }

    fun updateProfile(profile_pic_path: String,
                      _name: String,
                      _gender: String,
                      _email: String,
                      _categories: String){
        val user_id =getUserId().toString()
        try {
            val file = File(profile_pic_path) // Replace with your file's path
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val profilePic = MultipartBody.Part.createFormData("profile_pic", file.name, requestFile)
            val userId = user_id.toRequestBody("text/plain".toMediaTypeOrNull())
            val name = _name.toRequestBody("text/plain".toMediaTypeOrNull())
            val gender = _gender.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = _email.toRequestBody("text/plain".toMediaTypeOrNull())
            val categories = _categories.toRequestBody("text/plain".toMediaTypeOrNull())
            viewModelScope.launch {
                val result=apiRepository.updateProfile(
                    profilePic,
                    userId,
                    name,
                    gender,
                    email,
                    categories
                    )
                Log.d("CATEGORIES",_categories.toString())
                when (result){
                    is ApiResult.Failure ->{
                        _Error.value = result.error.toString()
                    }
                    is ApiResult.Success ->{
                        val data = result.data
                        Log.d("updateProfile",data.toString())
                        if(data.status.isSuccess()){

                            _message.value =data.message
                        }
                        else{
                            _Error.value=data.message
                        }
                    }

                }

            }
        }
        catch (e:Exception){
            Log.d("updateProfile Exception",e.toString())
            _Error.value=e.toString()
        }
    }

}

    





