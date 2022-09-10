package com.example.websiteparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.websiteparser.`interface`.ApiInterface
import com.example.websiteparser.adapter.RecyclerViewAdapter
import com.example.websiteparser.databinding.ActivityMainBinding
import com.example.websiteparser.model.Item
import com.example.websiteparser.model.Result
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bring up of layout though binding
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items: MutableList<Item> = mutableListOf() //initialization of empty list that will fill data of RecyclerView

        //Setup of RecyclerView adapter
        val adapter = RecyclerViewAdapter(this, items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.search.setOnClickListener { //sets up the click event for search action
            if(binding.editText.text.toString().isNotEmpty()){ //checks if textInput is not empty
                CoroutineScope(Dispatchers.IO).launch { //launches background task that will fetch data from the website
                    val apiInterface = ApiInterface.create().getSearch(3, binding.editText.text.toString(), 1, 1) //sets up the fetch interface

                    apiInterface.enqueue( object : Callback<Result> { //Fetching from the website
                        override fun onResponse(call: Call<Result>, response: Response<Result>){
                            if(response?.body()==null) {

                                println("The Message body is empty")
                                return
                            }

                            for(i in 0 until items.size){ //clearing the data list for new data
                                items.removeLast()
                            }

                            for(i in 0 until response.body()!!.items.size){ //populating list with new fetched data
                                   items.add(response.body()!!.items[i])
                            }
                            adapter.notifyDataSetChanged()

                        }

                        override fun onFailure(call: Call<Result>?, t: Throwable?){
                            t?.printStackTrace()
                        }
                    })
                }
            }


        }
    }
}