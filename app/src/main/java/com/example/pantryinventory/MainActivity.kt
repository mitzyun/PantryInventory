package com.example.pantryinventory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

var testAdapter: CustomAdapter? = null
var itemNames = ArrayList<String>()
var itemQuantities = ArrayList<String>()
var testList = ArrayList<PantryItem>()
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
            intent.putExtra("itemNames", itemNames)
            intent.putExtra("itemQuantities", itemQuantities)
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

    fun updateList() {
        for (i in 0 until itemNames.count()) {
            testList.add(PantryItem(itemNames[i], itemQuantities[i]))
        }
        testAdapter = CustomAdapter(this, testList)
        listView.adapter = testAdapter
    } // updateList

} // MainActivity

class PantryItem (name:String, quantity:String) {
    var itemName = name
    var itemQuantity = quantity.toInt()
    lateinit var buttonIncrease : ImageButton
    lateinit var buttonDecrease : ImageButton

} // PantryItem

class CustomAdapter(private val context: Context, private val arrayList: java.util.ArrayList<PantryItem>) : BaseAdapter() {
    private lateinit var itemName: TextView
    private lateinit var itemQuantity: TextView
    private lateinit var increaseButton: ImageButton
    private lateinit var decreaseButton: ImageButton

    override fun getCount(): Int {
        return arrayList.size
    } // getCount
    override fun getItem(position: Int): Any {
        return position
    } // getItem
    override fun getItemId(position: Int): Long {
        return position.toLong()
    } // getItemId
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.pantry_item, parent, false)
        itemName = convertView.findViewById(R.id.name)
        itemQuantity = convertView.findViewById(R.id.quantity)
        increaseButton = convertView.findViewById(R.id.increaseItem)
        decreaseButton = convertView.findViewById(R.id.decreaseItem)
        itemName.text = " " + arrayList[position].itemName
        itemQuantity.text = arrayList[position].itemQuantity.toString()
        //increaseButton.text = arrayList[position].mobileNumber
        return convertView
    } // getView
} // CustomAdapter