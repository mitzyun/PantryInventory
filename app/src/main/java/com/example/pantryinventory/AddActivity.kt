package com.example.pantryinventory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity()  {
    lateinit var name : EditText
    lateinit var quantity : EditText
    var localItem = ArrayList<String>()
    var localQuantity = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        name = findViewById(R.id.itemName)
        quantity = findViewById(R.id.itemQuantity)

    }//onCreate

    fun addItem(view : View){
        if (name.text.isNotEmpty() || name.text.isNotBlank()) {
            localItem.add(name.text.toString())
        }
        else {
            localItem.add("item")
        }
        if (quantity.text.isNotEmpty() || quantity.text.isNotBlank()) {
            localQuantity.add(quantity.text.toString())
        }
        else {
            localQuantity.add("0")
        }
        sharedPreferences = applicationContext.getSharedPreferences(
            "com.example.pantryinventory", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(
            "itemNames", ObjectSerializer.serialize(localItem))
            .apply()
        sharedPreferences.edit().putString(
            "itemQuantities", ObjectSerializer.serialize(localQuantity))
            .apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }//addItem

} // AddActivity