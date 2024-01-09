package org.geniadynamics.housify.ui.home
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.target.Target
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import org.geniadynamics.housify.R
import org.geniadynamics.housify.data.model.Item

//class InferenceRequestAdapter {
//}

class MyAdapter(private val context: Context, private var items: List<Item>) :

    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainImage: ImageView = view.findViewById(R.id.mainImage)
        val userText: TextView = view.findViewById(R.id.textView)
        val additionalText: TextView = view.findViewById(R.id.additionalText)
        val title: TextView = view.findViewById(R.id.titleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inference_info_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.userText.text = item.userText
        holder.additionalText.text = item.additionalText
        holder.title.text = item.title

//        Glide.with(context)
//            .load(item.imageUrl)
////            .placeholder(android.R.drawable.screen_background_light_transparent)
//            .into(holder.mainImage)
        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(android.R.drawable.screen_background_light_transparent)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e("Glide", "Load failed for ${item.imageUrl}", e)
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.d("Glide", "Resource ready for ${item.imageUrl}")
                    return false
                }
            }).apply(
                RequestOptions()
                .timeout(5000))
            .into(holder.mainImage)

    }

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}
