package ru.yellowshark.dsr_weather.ui.triggers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.databinding.ItemTriggerBinding
import ru.yellowshark.dsr_weather.domain.model.Trigger

class TriggersAdapter(
    private val onTriggerClickListener: (Trigger) -> Unit
) : RecyclerView.Adapter<TriggerViewHolder>() {

    var data: List<Trigger> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriggerViewHolder =
        TriggerViewHolder.create(parent)

    override fun onBindViewHolder(holder: TriggerViewHolder, position: Int) {
        holder.bind(data[position], onTriggerClickListener)
    }

    override fun getItemCount(): Int = data.size
}

class TriggerViewHolder(
    private val binding: ItemTriggerBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): TriggerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTriggerBinding.inflate(inflater, parent, false)
            return TriggerViewHolder(binding)
        }
    }

    fun bind(trigger: Trigger, onTriggerClickListener: (Trigger) -> Unit) {
        with(binding) {
            itemTriggerNameText.text = trigger.name
            root.setOnClickListener { onTriggerClickListener(trigger) }
            itemTriggerSettingsImage.setOnClickListener { onTriggerClickListener(trigger) }
        }
    }
}