package com.example.organizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: List<Task>, context: Context) :  RecyclerView.Adapter<TasksAdapter.TaskViewHolder>(){

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descTextView: TextView = itemView.findViewById(R.id.descTextView)
        val deadLineTextView: TextView= itemView.findViewById(R.id.deadLineTextView)
        val priorityTextView: TextView= itemView.findViewById(R.id.priorityTextView)
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
    }

    fun refreshData(newTasks: List<Task>)
    {
        tasks= newTasks
        notifyDataSetChanged()
    }

}