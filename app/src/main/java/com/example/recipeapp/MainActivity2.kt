package com.example.recipeapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textView = findViewById<TextView>(R.id.textView)
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        //progress
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val call: Call<List<RecipeDetails.Datum>> = apiInterface!!.getUser()

        call?.enqueue(object : Callback<List<RecipeDetails.Datum>> {
            override fun onResponse(
                call: Call<List<RecipeDetails.Datum>>,
                response: Response<List<RecipeDetails.Datum>>
            )
            {
                progressDialog.dismiss()
                val resource: List<RecipeDetails.Datum>? = response.body()
                var recipeData:String? = "";
                for(Recipe in resource!!){
                    recipeData = recipeData +Recipe.title+ "\n"+Recipe.author + "\n"+Recipe.ingredients + "\n"+Recipe.instructions +"\n"+"\n"
                }
                textView.text= recipeData
            }
            override fun onFailure(call: Call<List<RecipeDetails.Datum>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
            }
        })
    }
}