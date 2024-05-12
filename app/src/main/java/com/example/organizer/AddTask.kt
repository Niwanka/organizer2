package com.example.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.organizer.databinding.ActivityAddTaskBinding
import android.app.DatePickerDialog
import android.widget.Button
import java.util.*

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: taskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = taskDatabaseHelper(this)

        // Define the list of priority values
        val priorities = listOf("1", "2", "3", "4")

        // Set up the adapter for the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addTaskPrioritySpinner.adapter = adapter

        binding.saveButton.setOnClickListener {
            val title = binding.addTaskHead.text.toString()
            val content = binding.addTaskDescription.text.toString()
            val deadline = binding.addTaskDeadlineButton.text.toString()
            val priority = binding.addTaskPrioritySpinner.selectedItem.toString() // Get selected priority from Spinner

            val task = Task(0, title, content, deadline, priority)
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }

        binding.addTaskDeadlineButton.setOnClickListener {
            showDatePickerDialog()
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
                binding.addTaskDeadlineButton.text = selectedDate
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }
}
