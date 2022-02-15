package com.example.pantryinventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var pantryList : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("com.example.pantryinventory", MODE_PRIVATE)
        var pantry = ArrayList<String>()
        pantryList = findViewById(R.id.pantryList)

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

    fun updateList(){
        var item = intent.getStringExtra("Item")
        var quantity = intent.getIntExtra("Quantity", 1)
    }//updateList

} // MainActivity