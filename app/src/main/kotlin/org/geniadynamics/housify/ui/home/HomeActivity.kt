package org.geniadynamics.housify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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
    private lateinit var viewModel: InferenceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeComponents()
        setupRecyclerView()
        setupUserInteraction()


        viewModel.getResponses().observe(this, Observer { data ->
            adapter = InferenceAdapter(data)
            recyclerView.adapter = adapter
        })

        viewModel.getUserRequests("diogo.bernardo.dev@gmail.com")
    }



    private fun initializeComponents() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val repository = InferenceRepository(apiService)
        val factory = InferenceViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(InferenceViewModel::class.java)
    }

    private fun handleSendButtonClick(userInputTextView: TextView, progressBar: ProgressBar, sendButton: Button) {
        val userInput = userInputTextView.text.toString()
        val request = InferenceRequest(input = userInput, user = "diogo.bernardo.dev@gmail.com")

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

    private fun handleChipSelection(checkedId: Int) {
        when (checkedId) {
            R.id.EditOpt -> {
                Toast.makeText(this, "Edit option selected", Toast.LENGTH_SHORT).show()
                // Additional logic for Edit option
            }
            R.id.GenOpt -> {
                Toast.makeText(this, "Generate option selected", Toast.LENGTH_SHORT).show()
                // Additional logic for Generate option
            }
            R.id.DiscOpt -> {
                Toast.makeText(this, "Discover option selected", Toast.LENGTH_SHORT).show()
                // Additional logic for Discover option
            }
        }
    }

    private fun setupUserInteraction() {
        val sendButton = findViewById<Button>(R.id.btnSend)
        val userInputTextView = findViewById<TextView>(R.id.editText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val chipGroup = findViewById<ChipGroup>(R.id.chip)

        val defaultChip = findViewById<Chip>(R.id.GenOpt)
        defaultChip.isChecked = true

        sendButton.setOnClickListener {
            handleSendButtonClick(userInputTextView, progressBar, sendButton)
        }

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            handleChipSelection(checkedId)
        }
    }



    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.InfRequestRview)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = false

        recyclerView.layoutManager = layoutManager

        adapter = InferenceAdapter(emptyList())
        recyclerView.adapter = adapter
    }

}