package com.myktapp.viewmodel

import androidx.lifecycle.ViewModel
import io.realm.Realm


class UserModel:ViewModel() {
    private var realm= Realm.getDefaultInstance()
//    fun adduser(uname:String){
//        realm.executeTransaction{
//            var username = it.createObject(UserDetail::class.java,UUID.randomUUID().toString())
//            username.name = uname
//            realm.insertOrUpdate(username)
//
//        }
//    }
}