package com.ecoprogers.ecothon

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class EcothonApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Keys.MAPKIT_KEY)
    }
}