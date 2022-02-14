package com.example.pantryinventory

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity: AppCompatActivity()  {
    lateinit var itemName : EditText
    lateinit var itemQuantity : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        itemName = findViewById(R.id.itemName)
        itemQuantity = findViewById(R.id.itemQuantity)

    }//onCreate

} // AddActivity