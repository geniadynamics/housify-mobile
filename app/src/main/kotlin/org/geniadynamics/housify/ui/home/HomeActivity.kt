package org.geniadynamics.housify.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.camera.CameraActivity
import org.geniadynamics.housify.ui.visimage.VisImageActivity
import org.geniadynamics.housify.viewmodel.InferenceRequestViewModel
import org.geniadynamics.housify.data.model.Item

class HomeActivity : AppCompatActivity() {

    private val viewModel: InferenceRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnOpenCamera = findViewById<Button>(R.id.btnOpenCamera)
        btnOpenCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.InfRequestRview)
        val adapter = MyAdapter(this, viewModel.items.value ?: emptyList())

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(item: Item) {
                val intent = Intent(this@HomeActivity, VisImageActivity::class.java)
                intent.putExtra("userText", item.userText)
                intent.putExtra("imageUrl", item.imageUrl)
                intent.putExtra("additionalText", item.additionalText)
                intent.putExtra("title", item.title)
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.items.observe(this) { items ->
            adapter.updateItems(items)
        }
    }
}
