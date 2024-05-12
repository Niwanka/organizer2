package com.example.organizer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: List<Task>, context: Context) :  RecyclerView.Adapter<TasksAdapter.TaskViewHolder>(){

    private val db: taskDatabaseHelper= taskDatabaseHelper(context)

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descTextView: TextView = itemView.findViewById(R.id.descTextView)
        val deadLineTextView: TextView= itemView.findViewById(R.id.deadLineTextView)
        val priorityTextView: TextView= itemView.findViewById(R.id.priorityTextView)
        val updateButton: ImageButton= itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageButton= itemView.findViewById(R.id.deleteButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Inflate the item layout and create a new ViewHolder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // Return the size of the dataset
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Bind data to views within each item
        val currentTask = tasks[position]
        holder.titleTextView.text = currentTask.title
        holder.descTextView.text = currentTask.description
        holder.deadLineTextView.text = currentTask.deadline
        holder.priorityTextView.text = currentTask.priority

        holder.updateButton.setOnClickListener {
            val intent= Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply{
                putExtra("task_id", currentTask.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.deleteButton.setOnClickListener {
            db.deleteTask(currentTask.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newTasks: List<Task>)
    {
        tasks= newTasks
        notifyDataSetChanged()
    }

}