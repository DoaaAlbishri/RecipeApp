package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @GET("/recipes/")
    fun getUser(): Call<List<RecipeDetails.Datum>>

    @POST("/recipes/")
    fun addRecipes(@Body recipeData: RecipeDetails.Datum): Call<RecipeDetails.Datum>
}