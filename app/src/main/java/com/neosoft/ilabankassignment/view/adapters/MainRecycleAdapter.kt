package com.neosoft.ilabankassignment.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neosoft.ilabankassignment.databinding.RowTextBinding

class MainRecycleAdapter(private var dataSet: List<String>) :
    RecyclerView.Adapter<MainRecycleAdapter.ViewHolder>() {
    private var matchedData: ArrayList<String> = arrayListOf()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(binding: RowTextBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textView
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            RowTextBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position]
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun search(text: String?) {
        matchedData = arrayListOf()

        text?.let {
            dataSet.forEach { data ->
                if (data.contains(text, true) ||
                    data.contains(text, true)
                ) {
                    matchedData.add(data)
                }
            }
            dataSet = matchedData
            notifyDataSetChanged()
        }
    }

    fun updateList(dummyList: List<String>) {
        dataSet = dummyList
        notifyDataSetChanged()

    }
}