package com.myktapp.dataclsforinfo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class UserDetail(
    @PrimaryKey
    var id: String = "" ,
    var name: String = ""
// Other fields...
): RealmObject()