package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = findViewById<EditText>(R.id.edTitle)
        val name = findViewById<EditText>(R.id.edName)
        val Ingredents = findViewById<EditText>(R.id.edIngredents)
        val Instruction = findViewById<EditText>(R.id.edInstruction)
        val savebtn = findViewById<Button>(R.id.btsave)
        val view = findViewById<Button>(R.id.btview)
        view.setOnClickListener {
            intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        }
        savebtn.setOnClickListener {
            var Recipe = RecipeDetails.Datum(
                title.text.toString(),
                name.text.toString(),
                Ingredents.text.toString(),
                Instruction.text.toString()
            )

            addUserdata(Recipe, onResult = {
                title.setText("")
                name.setText("")
                Ingredents.setText("")
                Instruction.setText("")
            })
        }
    }

    private fun addUserdata(recipe: RecipeDetails.Datum, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addRecipes(recipe).enqueue(object : Callback<RecipeDetails.Datum> {
                override fun onResponse(
                    call: Call<RecipeDetails.Datum>,
                    response: Response<RecipeDetails.Datum>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<RecipeDetails.Datum>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
}