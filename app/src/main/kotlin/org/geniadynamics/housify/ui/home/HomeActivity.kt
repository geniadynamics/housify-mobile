package org.geniadynamics.housify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.geniadynamics.housify.R
import org.geniadynamics.housify.data.model.InferenceRequest
import org.geniadynamics.housify.data.model.InferenceResponse
import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.viewmodel.InferenceViewModel
import org.geniadynamics.housify.viewmodel.InferenceViewModelFactory
import org.geniadynamics.housify.data.network.ApiService
import org.geniadynamics.housify.data.network.config.RetrofitClient
import org.geniadynamics.housify.data.repository.InferenceRepository
import org.geniadynamics.housify.ui.camera.CameraActivity
import org.geniadynamics.housify.ui.userinfo.UserInfoActivity
import org.geniadynamics.housify.ui.visimage.VisImageActivity
import org.geniadynamics.housify.ui.welcome.WelcomeActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: InferenceViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InferenceAdapter
    private lateinit var userEmail : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        if (sharedPreferences != null) {
            userEmail = sharedPreferences.getString("isLoggedIn", "user")!!
        }

        initializeComponents()
        setupRecyclerView()
        setupUserInteraction()

        viewModel.getResponses().observe(this, Observer { data ->
            adapter = InferenceAdapter(this, data)
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(item: InferenceResponse) {
                    val intent = Intent(this@HomeActivity, VisImageActivity::class.java)
                    intent.putExtra("userText", item.input)
                    intent.putExtra("imageUrl", item.img_output)
                    intent.putExtra("additionalText", item.output_description)
                    intent.putExtra("title", item.output_classification)
                    startActivity(intent)
                }
            })
        })

        viewModel.getUserRequests(userEmail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_1 -> {
                showLogoutConfirmationDialog()
                true
            }
            R.id.item_2 -> {
                val intent = Intent(this, UserInfoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item_3 -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showLogoutOption(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            onOptionsItemSelected(item)
        }
        popupMenu.show()
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Do you really want to logout?")
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(this, WelcomeActivity::class.java)
            val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            sharedPreferences?.edit()?.remove("isLoggedIn")?.apply()
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.create().show()
    }

    private fun initializeComponents() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val repository = InferenceRepository(apiService)
        val factory = InferenceViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(InferenceViewModel::class.java)
    }

    private fun handleSendButtonClick(userInputTextView: TextView, progressBar: ProgressBar, sendButton: Button) {
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

        val btnOpenCamera = findViewById<Button>(R.id.btnOpenCamera)
        btnOpenCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
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

        adapter = InferenceAdapter(this, data = emptyList())
        recyclerView.adapter = adapter
    }
}
