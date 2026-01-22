package com.example.pantherxcore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pantherxcore.databinding.ItemExerciseBinding
import com.example.pantherxcore.databinding.ItemExerciseHeaderBinding


class ExerciseAdapter(
    private val onExerciseClick: (Exercise) -> Unit,
    private val selectedColorOption: Int,
    private val isBackgroundChanged: Boolean  // 🔹 Nuevo parámetro
) : ListAdapter<ExerciseListItem, RecyclerView.ViewHolder>(ExerciseListItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_EXERCISE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ExerciseListItem.Header -> VIEW_TYPE_HEADER
            is ExerciseListItem.ExerciseItem -> VIEW_TYPE_EXERCISE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemExerciseHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_EXERCISE -> {
                val binding = ItemExerciseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ExerciseViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ExerciseListItem.Header -> (holder as HeaderViewHolder).bind(
                item,
                isBackgroundChanged  // 🔹 Pasar el estado del switch
            )
            is ExerciseListItem.ExerciseItem -> (holder as ExerciseViewHolder).bind(
                item.exercise,
                selectedColorOption
            )
        }
    }

    // ViewHolder para los headers
    inner class HeaderViewHolder(
        private val binding: ItemExerciseHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: ExerciseListItem.Header, isBackgroundChanged: Boolean) {
            binding.tvHeaderTitle.text = header.title

            // 🔹 Cambiar el color del título según el estado del switch
            val textColorRes = if (isBackgroundChanged) {
                R.color.whiter  // Texto claro cuando el fondo es oscuro
            } else {
                R.color.darker  // Texto blanco cuando el fondo es claro
            }

            binding.tvHeaderTitle.setTextColor(
                ContextCompat.getColor(itemView.context, textColorRes)
            )
        }
    }

    // ViewHolder para los ejercicios
    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise, colorOption: Int) {
            binding.tvExerciseName.text = exercise.name
            binding.ivExerciseIcon.setImageResource(exercise.imageRes)

            // Aplicar el color según la opción seleccionada
            val colorRes = when (colorOption) {
                1 -> R.color.purpura
                2 -> R.color.amarillo
                3 -> R.color.orange
                4 -> R.color.gray
                else -> R.color.purpura
            }
            binding.cardExercise.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, colorRes)
            )

            binding.root.setOnClickListener {
                onExerciseClick(exercise)
            }
        }
    }

    class ExerciseListItemDiffCallback : DiffUtil.ItemCallback<ExerciseListItem>() {
        override fun areItemsTheSame(
            oldItem: ExerciseListItem,
            newItem: ExerciseListItem
        ): Boolean {
            return when {
                oldItem is ExerciseListItem.Header && newItem is ExerciseListItem.Header ->
                    oldItem.type == newItem.type
                oldItem is ExerciseListItem.ExerciseItem && newItem is ExerciseListItem.ExerciseItem ->
                    oldItem.exercise.id == newItem.exercise.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: ExerciseListItem,
            newItem: ExerciseListItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}