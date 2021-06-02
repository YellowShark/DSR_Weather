package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.domain.model.Location

class LocationsAdapter(
    private val onLocationClickListener: (Location) -> Unit,
    private val onStarClickListener: (Int, Int, Boolean) -> Unit,
) : RecyclerView.Adapter<LocationsViewHolder>() {

    private var data: ArrayList<Location> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder =
        LocationsViewHolder.create(parent)

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(position, data[position], onLocationClickListener, onStarClickListener)
    }

    override fun getItemCount(): Int = data.size

    fun addItem(location: Location) {
        data.add(location)
        data.sortBy { it.city }
        notifyDataSetChanged()
    }

    fun clearData() {
        if (data.isNotEmpty()) {
            data.clear()
            notifyDataSetChanged()
        }
    }

    fun updateLocation(pos: Int) {
        val location = data[pos]
        location.isFavorite = !location.isFavorite
        data[pos] = location
        notifyItemChanged(pos)
    }

    fun removeItem(pos: Int) {
        data.removeAt(pos)
        //notifyItemChanged(pos)
        notifyDataSetChanged()
    }

    fun setData(list: List<Location>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}