package org.geniadynamics.housify.ui.home


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.camera.CameraActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.geniadynamics.housify.viewmodel.InferenceRequestViewModel


class HomeActivity: AppCompatActivity() {

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.items.observe(this) { items ->
            adapter.updateItems(items)
        }

    }
}