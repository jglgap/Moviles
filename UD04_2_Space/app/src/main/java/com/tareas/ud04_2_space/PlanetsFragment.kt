package com.tareas.ud04_2_space

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView


class PlanetsFragment : Fragment() {

    data class Planet(
        val name: String,
        val distanceFromSun: Double,
        val info: String,
        val atmosphere: String,
        val orbitDays: Int
    )

    private fun loadPlanets(context: Context): List<Planet> {
        val res = context.resources
        val names = res.getStringArray(R.array.planet_names)
        val distances = res.getStringArray(R.array.planet_distances)
        val infos = res.getStringArray(R.array.planet_information)
        val atmospheres = res.getStringArray(R.array.planet_atmospheres)
        val orbitDays = res.getStringArray(R.array.planet_orbit_days)

        return names.indices.map { i ->
            Planet(
                name = names[i],
                distanceFromSun = distances[i].toDouble(),
                info = infos[i],
                atmosphere = atmospheres[i],
                orbitDays = orbitDays[i].toInt()
            )
        }
    }

    private fun createMaterialCards(context: Context, planets : List<Planet>) : List<MaterialCardView> {

        val cardViews = ArrayList<MaterialCardView>()

        for (planet in planets){
            val cardView = MaterialCardView(context).apply {
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = (8 * context.resources.displayMetrics.density).toInt()
                    setMargins(margin, margin, margin, margin)
                }
            }
            cardViews.add(cardView)
        }


        return cardViews
    }

    private fun inflateMaterialCardViews()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planets, container, false)
    }

}
