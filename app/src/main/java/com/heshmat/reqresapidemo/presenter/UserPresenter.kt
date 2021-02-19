package com.heshmat.reqresapidemo.presenter

import com.heshmat.reqresapidemo.model.ApiResponseModel
import com.heshmat.reqresapidemo.service.RetrofitInit
import com.heshmat.reqresapidemo.ui.view.UserView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(val userView: UserView) {
    fun getUsers(pageNum: Int) {
        userView.loading()
        RetrofitInit.apiInterface.fetchUsers(pageNum).enqueue(object :
            Callback<ApiResponseModel> {

            override fun onResponse(
                call: Call<ApiResponseModel>,
                response: Response<ApiResponseModel>
            ) {


                if (response.isSuccessful) {
                    var apResponseModel: ApiResponseModel? = response.body()
                    if (apResponseModel != null) {
                        userView.success()
                        userView.usersReady(apResponseModel.data)
                    }


                }
            }

            override fun onFailure(call: Call<ApiResponseModel>, t: Throwable) {
                userView.fail(t)

            }

        })
    }

}