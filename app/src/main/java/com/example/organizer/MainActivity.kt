package com.example.organizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    private lateinit var db: taskDatabaseHelper
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= taskDatabaseHelper(this)
        tasksAdapter= TasksAdapter(db.getAllTasks(), this)

        binding.tasksRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.tasksRecyclerView.adapter= tasksAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        tasksAdapter.refreshData(db.getAllTasks())
    }
}