package com.example.pantryinventory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity()  {
    lateinit var name : EditText
    lateinit var quantity : EditText
    lateinit var confirmButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        name = findViewById(R.id.itemName)
        quantity = findViewById(R.id.itemQuantity)
        confirmButton = findViewById(R.id.confirmButton)
    }//onCreate

    fun addItem(view : View){
        itemNames.add(name.text.toString())
        itemQuantities.add(quantity.text.toString())

        sharedPreferences = applicationContext.getSharedPreferences(
            "com.example.pantryinventory", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(
            "itemNames", ObjectSerializer.serialize(itemNames))
            .apply()
        sharedPreferences.edit().putString(
            "itemQuantities", ObjectSerializer.serialize(itemQuantities))
            .apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }//addItem

} // AddActivity