package com.example.websiteparser.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.websiteparser.databinding.RowLayoutBinding
import com.example.websiteparser.model.Item


class RecyclerViewAdapter(val context: Context, val items: MutableList<Item>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, position: Int){
            //setting up the title and text to adequate views
            binding.title.text = item.title
            val text = item.text.replace("\\s+".toRegex(), " ")
            if(text[0] == ' '){
                binding.text.text = text.drop(1)
            }else{
                binding.text.text = text
            }

            binding.title.setOnClickListener {  //on Click on title that will open link to specific website
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pomoc.bluemedia.pl" + item.link))
                context.startActivity(browserIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)

    override fun getItemCount(): Int = items.size
}