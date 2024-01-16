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
import org.geniadynamics.housify.data.model.InferenceResponse


////class InferenceRequestAdapter {
////}
interface OnItemClickListener {
    fun onItemClick(item: InferenceResponse)
}
//
//class MyAdapter(private val context: Context, private var items: List<InferenceResponse>) :
//    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    private var itemClickListener: OnItemClickListener? = null
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.itemClickListener = listener
//    }
//
//    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val mainImage: ImageView = view.findViewById(R.id.mainImage)
//        val userText: TextView = view.findViewById(R.id.textView)
//        val additionalText: TextView = view.findViewById(R.id.additionalText)
//        val title: TextView = view.findViewById(R.id.titleText)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.inference_info_home, parent, false)
//        return MyViewHolder(view)
//    }
//
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = items[position]
//        holder.title.text = item.output_classification
//        holder.userText.text = item.input
//        holder.additionalText.text = item.output_description
//
//        val imgUrl = "https://housify.geniadynamics.org/media/gen/" + item.img_output + ".png"
//
////        Glide.with(context)
////            .load(item.imageUrl)
//////            .placeholder(android.R.drawable.screen_background_light_transparent)
////            .into(holder.mainImage)
//        Glide.with(context)
//            .load(imgUrl)
//            .placeholder(android.R.drawable.screen_background_light_transparent)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                    Log.e("Glide", "Load failed for $imgUrl", e)
//                    return false
//                }
//
//                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    Log.d("Glide", "Resource ready for $imgUrl")
//                    return false
//                }
//            }).apply(
//                RequestOptions()
//                .timeout(5000))
//            .into(holder.mainImage)
//
//        holder.mainImage.setOnClickListener {
//            itemClickListener?.onItemClick(item)
//        }
//
//    }
//
//    fun updateItems(newItems: InferenceResponse) {
//        items = newItems
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount() = items.size
//}
class InferenceAdapter(private val context: Context, private val data: List<InferenceResponse>) :
    RecyclerView.Adapter<InferenceAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        Log.d("InferenceAdapter", "Image clicked")
        this.itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userText: TextView = view.findViewById(R.id.textView)
        val mainImage: ImageView = view.findViewById(R.id.mainImage)
        val additionalText: TextView = view.findViewById(R.id.additionalText)
        val titleText: TextView = view.findViewById(R.id.titleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inference_info_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.userText.text = item.input
        Glide.with(context).load(item.img_output).into(holder.mainImage)
        holder.additionalText.text = item.output_description
        holder.titleText.text = item.output_classification

        holder.mainImage.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = data.size
}