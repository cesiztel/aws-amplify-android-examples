package com.cesarcodecrafter.awsamplifyanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.analytics.AnalyticsEvent
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_product.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        val myProducts = arrayOf(
            Product(1, R.drawable.coconout, "Coconout", "Full of fat", "3.57"),
            Product(2, R.drawable.mayones, "Mayonnaise", "Add to your salads", "1.65"),
            Product(3, R.drawable.yogurt, "Yogurt", "Or an alternative", "1.57"))
        viewAdapter = MyAdapter(myProducts)

        recyclerView = products_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }
    }
}

data class Product(val identifier: Int, val image: Int, val title: String, val subTitle: String, val price: String)

class MyAdapter(private val myDataset: Array<Product>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.product_image_view.setImageResource(myDataset[position].image)
        holder.view.product_title_textview.text = myDataset[position].title
        holder.view.product_subtitle_textview.text = myDataset[position].subTitle
        holder.view.product_price_textview.text = myDataset[position].price

        holder.view.setOnClickListener {
            recordProductEvent(myDataset[position].identifier)

            Toast.makeText(it.context, "Product visited send", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun recordProductEvent(productId: Int) {
        val userId = Amplify.Auth.currentUser.userId
        Amplify.Analytics.identifyUser(userId, null)

        val event: AnalyticsEvent = AnalyticsEvent.builder()
            .name("ProductVisited")
            .addProperty("Channel", "Android app")
            .addProperty("ProductId", productId)
            .build()

        Amplify.Analytics.recordEvent(event)
    }

    override fun getItemCount() = myDataset.size
}