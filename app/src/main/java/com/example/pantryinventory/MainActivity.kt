package com.example.pantryinventory

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

lateinit var arrayAdapter: ArrayAdapter<*>
var itemNames = ArrayList<String>()
var itemQuantities = ArrayList<String>()
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
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemNames)
        listView.adapter = arrayAdapter

    }//updateList

} // MainActivity