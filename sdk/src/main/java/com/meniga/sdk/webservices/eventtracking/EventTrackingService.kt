package com.meniga.sdk.webservices.eventtracking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 31.5.2018.
 */
internal interface EventTrackingService {
    @POST("eventtracking")
    fun trackEvent(@Body body: TrackEvent): Call<Void>
}
