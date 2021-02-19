package com.heshmat.reqresapidemo.ui.view

import com.heshmat.reqresapidemo.model.User

interface UserView {
    fun usersReady(userArrList: List<User>)
    fun  loading()
    fun success()
    fun fail(t:Throwable)
}