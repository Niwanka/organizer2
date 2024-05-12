package com.example.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.organizer.databinding.ActivityAddTaskBinding

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: taskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = taskDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.addTaskHeading.text.toString()
            val content = binding.addTaskDescription.text.toString()
            val deadline = binding.addTaskDeadLine.text.toString()
            val priority = binding.addTaskPriority.text.toString()

            val task = Task(0, title, content, deadline, priority)
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }


}