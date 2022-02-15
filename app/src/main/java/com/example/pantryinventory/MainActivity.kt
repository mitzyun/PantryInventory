package com.example.pantryinventory

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


lateinit var arrayAdapter: ArrayAdapter<*>
var itemNames = ArrayList<String>()
var itemQuantities = ArrayList<String>()
var combinedList = ArrayList<String>()
lateinit var sharedPreferences: SharedPreferences

class MainActivity : AppCompatActivity() {
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.pantryList)

        getData()
        updateList()
    } //onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.additem_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == R.id.addItem) {
            val intent = Intent(applicationContext, AddActivity::class.java)
            startActivity(intent)
            return true
        }
        return false
    }// onOptionsItemSelected

    fun getData(){
        sharedPreferences = getSharedPreferences(
            "com.example.pantryinventory", MODE_PRIVATE)

        var pantryList : ArrayList<String>
        pantryList = ObjectSerializer
            .deserialize(
                sharedPreferences
                    .getString("itemNames", ObjectSerializer.serialize(ArrayList<String>()))
            ) as ArrayList<String>

        if (pantryList.size != 0) {
            itemNames = java.util.ArrayList(pantryList)
        }

        pantryList = ArrayList<String>()
        pantryList = ObjectSerializer
            .deserialize(
                sharedPreferences
                    .getString("itemQuantities", ObjectSerializer.serialize(ArrayList<String>()))
            ) as ArrayList<String>

        if (pantryList.size != 0) {
            itemQuantities = java.util.ArrayList(pantryList)
        }

    }//getData

    fun updateList(){
        for(i in 0 until itemNames.count()){
            combinedList.add(itemNames[i] + " - " + itemQuantities[i])
        }
        arrayAdapter = ArrayAdapter(this, R.layout.pantry_item, R.id.name, combinedList)
        listView.adapter = arrayAdapter
    }//updateList

} // MainActivity