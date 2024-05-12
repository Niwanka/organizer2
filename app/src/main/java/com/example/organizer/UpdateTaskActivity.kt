package com.example.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.organizer.databinding.ActivityAddTaskBinding
import com.example.organizer.databinding.ActivityMainBinding

import com.example.organizer.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: taskDatabaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = taskDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskById(taskId)
        binding.updateTaskHead.setText(task.title)
        binding.updateTaskDescription.setText(task.description)
        binding.updateTaskDeadLine.setText(task.deadline)
        binding.updateTaskPriority.setText(task.priority)

        binding.updateSaveButton.setOnClickListener {
            val newTitle= binding.updateTaskHead.text.toString()
            val newDesc = binding.updateTaskDescription.text.toString()
            val newDeadline =binding.updateTaskDeadLine.text.toString()
            val newPriority = binding.updateTaskPriority.text.toString()

            val updatedTask= Task(taskId, newTitle, newDesc, newDeadline, newPriority)

            db.updateTask(updatedTask)
            finish()

            Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show()
        }

    }
}
