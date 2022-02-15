package com.example.pantryinventory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity()  {
    lateinit var itemName : EditText
    lateinit var itemQuantity : EditText
    lateinit var confirmButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        itemName = findViewById(R.id.itemName)
        itemQuantity = findViewById(R.id.itemQuantity)
        confirmButton = findViewById(R.id.confirmButton)
    }//onCreate

    fun addItem(view : View){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Item", itemName.text.toString())
        intent.putExtra("Quantity", itemQuantity.text.toString().toInt())
        startActivity(intent)
    }//addItem

} // AddActivity