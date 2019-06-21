package com.enkapp.molocat.ui.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enkapp.molocat.R
import com.enkapp.molocat.model.BreedShort

class BreedAdapter(var list: MutableList<BreedShort>?, var listener: OnBreedClickListener) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    fun setBreedList(listUpdate: MutableList<BreedShort>?){
        list = listUpdate
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BreedViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed: BreedShort? = list?.get(position)
        holder.bind(breed)
        holder.itemView.setOnClickListener {
            listener.clickOnBreed(list?.get(position)?.id)
        }
    }

    override fun getItemCount(): Int = if(list!=null) list?.size!! else 0


    inner class BreedViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.breed_item_list, parent, false)) {
        private var mName: TextView? = null
        private var mOrigin: TextView? = null
        private var mAffectionLevel: TextView? = null

        init {
            mName = itemView.findViewById(R.id.name)
            mOrigin = itemView.findViewById(R.id.origin)
            mAffectionLevel = itemView.findViewById(R.id.affection_level)
        }

        fun bind(breed: BreedShort?) {
            mName?.text = breed?.name
            mOrigin?.text = breed?.origin
            mAffectionLevel?.text = breed?.affectionLevel.toString()
        }

    }

    interface OnBreedClickListener{
        fun clickOnBreed(id: String?)
    }

}