package com.example.pantryinventory

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar.getInstance

//Michelle Yun and Victoria Lei

var customAdapter: CustomAdapter? = null
var itemNames = ArrayList<String>()
var itemQuantities = ArrayList<String>()
var pantryItemList = ArrayList<PantryItem>()
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
        pantryItemList.clear()
        for (i in 0 until itemNames.count()) {
            pantryItemList.add(PantryItem(itemNames[i], itemQuantities[i]))
        }
        customAdapter = CustomAdapter(this, pantryItemList)
        listView.adapter = customAdapter
    } // updateList

} // MainActivity

class PantryItem (name:String, quantity:String) {
    var itemName = name
    var itemQuantity = quantity

} // PantryItem

// source: https://www.tutorialspoint.com/how-to-write-a-custom-adapter-for-my-list-view-on-android-using-kotlin
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

        increaseButton.setOnClickListener {
            var quantity = arrayList[position].itemQuantity.toInt()
            quantity++
            arrayList[position].itemQuantity = quantity.toString()
            this.notifyDataSetChanged()
        } // increaseButton listener
        decreaseButton.setOnClickListener {
            var quantity = arrayList[position].itemQuantity.toInt()

            // check if item should be deleted
            if (quantity == 0) {
                val name = arrayList[position].itemName
                for (i in 0 until arrayList.count()) {
                    if (arrayList.get(i).itemName == name) {
                        removeView(i)
                        break
                    }
                }
            } else {
                quantity--
                arrayList[position].itemQuantity = quantity.toString()
            }
            this.notifyDataSetChanged()
        } // decreaseButton listener

        itemName.text = arrayList[position].itemName
        itemQuantity.text = arrayList[position].itemQuantity
        return convertView
    } // getView

    fun removeView(i : Int) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Confirm")
            .setMessage("Do you want to delete this item?")
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, x: Int ->
                itemNames.removeAt(i)
                itemQuantities.removeAt(i)
                arrayList.removeAt(i)
                sharedPreferences.edit()
                    .putString("iteNames", ObjectSerializer.serialize(itemNames))
                    .apply()
                sharedPreferences.edit()
                    .putString("itemQuantities", ObjectSerializer.serialize(itemQuantities))
                    .apply()
                this.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    } // removeView

} // CustomAdapter