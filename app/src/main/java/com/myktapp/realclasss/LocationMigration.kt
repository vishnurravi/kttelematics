package com.myktapp.realclasss

import io.realm.DynamicRealm
import io.realm.RealmMigration

class LocationMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        // Perform migration logic based on old and new schema versions
        if (oldVersion == 0L) {
            // Migration from version 0 to 1
            // Add or modify schema here
        }

        // Handle other migration cases if needed
    }
}