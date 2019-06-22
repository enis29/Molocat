package com.enkapp.molocat.ui.breed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enkapp.molocat.R
import com.enkapp.molocat.model.BreedShort

class BreedAdapter(val context: Context, var list: MutableList<BreedShort>?, var listener: OnBreedClickListener) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>(), Filterable {

    private var resultList: MutableList<BreedShort>? = null

    fun setBreedList(listUpdate: MutableList<BreedShort>?){
        list = listUpdate
        resultList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BreedViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed: BreedShort? = resultList?.get(position)
        holder.bind(breed)
        holder.itemView.setOnClickListener {
            listener.clickOnBreed(resultList?.get(position)?.id)
        }
    }

    override fun getItemCount(): Int = if(resultList!=null) resultList?.size!! else 0

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null) {
                    val searchString = constraint.toString()
                    if (searchString.isEmpty()) {
                        resultList = list
                    } else {
                        if(list?.isNotEmpty()!!) {
                            val filteredList = emptyList<BreedShort>().toMutableList()
                            for (row in list!!.listIterator()) {
                                if (row.name.toLowerCase().startsWith(searchString.toLowerCase())) {
                                    filteredList.add(row)
                                }
                            }
                            resultList = filteredList
                        }
                    }
                    val filterResults = Filter.FilterResults()
                    filterResults.values = resultList
                    return filterResults
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?,
                                        results: Filter.FilterResults?) = when {
                results?.count ?: -1 > 0 -> notifyDataSetChanged()
                else -> notifyDataSetChanged()
            }
        }
    }


    inner class BreedViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.breed_item_list, parent, false)) {
        private var mName: TextView? = null
        private var mOrigin: TextView? = null
        private var mTemperament: TextView? = null

        init {
            mName = itemView.findViewById(R.id.name)
            mOrigin = itemView.findViewById(R.id.origin)
            mTemperament = itemView.findViewById(R.id.temperament)
        }

        fun bind(breed: BreedShort?) {
            mName?.text = breed?.name
            mOrigin?.text = context.resources.getString(R.string.field_from).plus(breed?.origin)
            mTemperament?.text = breed?.temperament
        }

    }

    interface OnBreedClickListener{
        fun clickOnBreed(id: String?)
    }

}