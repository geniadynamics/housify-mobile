package org.geniadynamics.housify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.geniadynamics.housify.R
import org.geniadynamics.housify.data.model.InferenceRequest
import org.geniadynamics.housify.data.model.InferenceResponse
import org.geniadynamics.housify.data.network.ApiService
import org.geniadynamics.housify.data.network.config.RetrofitClient
import org.geniadynamics.housify.data.repository.InferenceRepository
import org.geniadynamics.housify.ui.camera.CameraActivity
import org.geniadynamics.housify.ui.visimage.VisImageActivity
import org.geniadynamics.housify.viewmodel.InferenceViewModel
import org.geniadynamics.housify.viewmodel.InferenceViewModelFactory

class HomeActivity : AppCompatActivity() {

//    private val viewModel: InferenceRequestViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        val btnOpenCamera = findViewById<Button>(R.id.btnOpenCamera)
//        btnOpenCamera.setOnClickListener {
//            val intent = Intent(this, CameraActivity::class.java)
//            startActivity(intent)
//        }
//
//        val recyclerView: RecyclerView = findViewById(R.id.InfRequestRview)
//        val adapter = MyAdapter(this, emptyList())
//
//        adapter.setOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClick(item: InferenceResponse) {
//                val intent = Intent(this@HomeActivity, VisImageActivity::class.java).apply {
//                    putExtra("userText", item.input)
//                    putExtra("imageUrl", "https://housify.geniadynamics.org/media/gen/" + item.img_output + ".png")
//                    putExtra("additionalText", item.output_description)
//                    putExtra("title", item.output_classification)
//                }
//                startActivity(intent)
//            }
//        })
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        viewModel.inferenceResponse.observe(this) { responses ->
//            adapter.updateItems(responses)
//        }
//    }
    private lateinit var viewModel: InferenceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val repository = InferenceRepository(apiService)
        val factory = InferenceViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(InferenceViewModel::class.java)
        setupRecyclerView()

        viewModel.getResponses().observe(this, Observer { data ->
            adapter = InferenceAdapter(data)
            recyclerView.adapter = adapter
        })

        val userEmail = "diogo.bernardo.dev@gmail.com"
        viewModel.getUserRequests(userEmail)

        val sendButton = findViewById<Button>(R.id.btnSend)
        val userInputTextView = findViewById<TextView>(R.id.editText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        sendButton.setOnClickListener {
            val userInput = userInputTextView.text.toString()
            val request = InferenceRequest(input = userInput, user = userEmail)

            progressBar.visibility = View.VISIBLE
            userInputTextView.isEnabled = false
            sendButton.isEnabled = false

            viewModel.postInferenceRequest(request).observe(this, Observer { response ->
                progressBar.visibility = View.GONE
                userInputTextView.isEnabled = true
                sendButton.isEnabled = true
                userInputTextView.text = ""
            })
        }

//        viewModel.getUserRequests(userEmail).observe(this, Observer { data ->
//            adapter = InferenceAdapter(data)
//            recyclerView.adapter = adapter
//        })

    }



    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.InfRequestRview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Adapter is initially empty
        adapter = InferenceAdapter(emptyList())
        recyclerView.adapter = adapter
    }
}