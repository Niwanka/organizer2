package com.example.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.organizer.databinding.ActivityUpdateTaskBinding
import java.util.*
import android.app.DatePickerDialog


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
        binding.updateTaskDeadlineButton.setText(task.deadline)

        // Define the list of priority values
        val priorities = listOf("1", "2", "3", "4")

        // Set up the adapter for the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.updateTaskPrioritySpinner.adapter = adapter
        val priorityIndex = priorities.indexOf(task.priority)
        if (priorityIndex != -1) {
            binding.updateTaskPrioritySpinner.setSelection(priorityIndex)
        }

        binding.updateTaskDeadlineButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTaskHead.text.toString()
            val newDesc = binding.updateTaskDescription.text.toString()
            val newDeadline = binding.updateTaskDeadlineButton.text.toString()
            val newPriority = binding.updateTaskPrioritySpinner.selectedItem.toString()

            val updatedTask = Task(taskId, newTitle, newDesc, newDeadline, newPriority)

            db.updateTask(updatedTask)
            finish()

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.updateTaskDeadlineButton.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }
}
