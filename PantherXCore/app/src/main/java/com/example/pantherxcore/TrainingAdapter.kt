package com.example.pantherxcore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class TrainingAdapter(
    private val onDetailsClick: (Training) -> Unit,
    var selectedColorOption: Int, // ahora mutable para actualizar dinámicamente
    var isButtonColorChanged: Boolean = false // estado del botón
) : ListAdapter<Training, TrainingAdapter.TrainingViewHolder>(TrainingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_training, parent, false)
        return TrainingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = getItem(position)
        holder.bind(training, selectedColorOption, isButtonColorChanged)
    }

    fun updateSelectedColor(newColorOption: Int) {
        selectedColorOption = newColorOption
        notifyDataSetChanged()
    }

    fun updateButtonColorState(isChanged: Boolean) {
        isButtonColorChanged = isChanged
        notifyDataSetChanged()
    }

    inner class TrainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTrainingName: TextView = itemView.findViewById(R.id.tvTrainingName)
        private val btnDetails: Button = itemView.findViewById(R.id.btnDetails)
        private val cardView: MaterialCardView = itemView as MaterialCardView

        fun bind(training: Training, colorOption: Int, isChanged: Boolean) {
            tvTrainingName.text = training.name

            // Color del botón según la variable del ViewModel
            val buttonColorRes = if (isChanged) R.color.darker else R.color.whiter
            btnDetails.backgroundTintList =
                ContextCompat.getColorStateList(itemView.context, buttonColorRes)
            val colorRes = if (isChanged) R.color.whiter else R.color.darker
            btnDetails.setTextColor(ContextCompat.getColor(itemView.context, colorRes))

            btnDetails.setOnClickListener {
                onDetailsClick(training)
            }

            // Opcional: mantener color del CardView según selectedColorOption
            val cardColorRes = when (colorOption) {
                1 -> R.color.purpura
                2 -> R.color.amarillo
                3 -> R.color.orange
                4 -> R.color.gray
                else -> R.color.purpura
            }
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, cardColorRes)
            )
        }
    }

    class TrainingDiffCallback : DiffUtil.ItemCallback<Training>() {
        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem == newItem
        }
    }
}

