package com.mttnow.coolestprojects.network

import retrofit2.http.GET


interface CoolestProjectsService {

  @GET("url")
  fun getStuff();
}