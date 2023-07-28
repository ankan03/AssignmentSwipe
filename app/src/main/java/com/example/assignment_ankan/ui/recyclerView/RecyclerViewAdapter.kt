package com.example.assignment_ankan.ui.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_ankan.R
import com.example.assignment_ankan.model.GetProductResponse
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.util.*

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var originalItems = ArrayList<GetProductResponse>()
    private var filteredItems = ArrayList<GetProductResponse>()

    fun setUpdateData(items:ArrayList<GetProductResponse>){
    originalItems = items
    filterByName("") // Initially show all items
    }

    fun filterByName(searchText: String) {
        filteredItems.clear()
        if (searchText.isBlank()) {
            filteredItems.addAll(originalItems)
        } else {
            val lowerCaseQuery = searchText.toLowerCase(Locale.getDefault())
            for (item in originalItems) {
                if (item.product_name.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredItems.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.image_file)
        val productName = view.findViewById<TextView>(R.id.product_name)
        val productType = view.findViewById<TextView>(R.id.product_type)
        val price = view.findViewById<TextView>(R.id.price)
        val tax = view.findViewById<TextView>(R.id.tax)

        fun bind(data:GetProductResponse){
            productName.text = data.product_name
            productType.text = data.product_type
            price.text = "₹ ${formatAmount(data.price)}"
            tax.text = formatAmount(data.tax)

            if (data.image != null && data.image.isNotEmpty()) {
                Picasso.get()
                    .load(data.image)
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.loading_image)
                    .into(image)
            } else {
                Picasso.get()
                    .load(R.drawable.loading_image)
                    .into(image)
            }
        }

        private fun formatAmount(price: String): CharSequence? {
            val decimalFormat = DecimalFormat("#.##")
            return decimalFormat.format(price.toDouble())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }
}