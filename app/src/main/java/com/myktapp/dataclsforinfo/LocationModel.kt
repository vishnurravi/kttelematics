package com.myktapp.dataclsforinfo

import io.realm.RealmObject


 class LocationModel(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
) : RealmObject()