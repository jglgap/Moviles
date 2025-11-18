package com.example.planets.model

import android.content.Context
import com.example.planets.R

data class Planet(
    val name: String,
    val distanceFromSun: Double,   // in million km
    val info: String,              // general description
    val atmosphere: String,        // main gases
    val orbitDays: Int,            // orbit period in Earth days
    val imageResId: Int            // drawable resource ID (R.drawable.*)
) {
    companion object {

        /**
         * Loads all planet data from the resource arrays in res/values/planets.xml.
         * It automatically matches drawable images by planet name (e.g. earth.png → Earth).
         */
        fun loadFromResources(context: Context): List<Planet> {
            val res = context.resources

            val names = res.getStringArray(R.array.planet_names)
            val distances = res.getStringArray(R.array.planet_distances)
            val infos = res.getStringArray(R.array.planet_information)
            val atmospheres = res.getStringArray(R.array.planet_atmospheres)
            val orbitDays = res.getStringArray(R.array.planet_orbit_days)

            return names.indices.map { i ->
                val imageRes = getPlanetImageRes(context, names[i])
                Planet(
                    name = names[i],
                    distanceFromSun = distances[i].toDouble(),
                    info = infos[i],
                    atmosphere = atmospheres[i],
                    orbitDays = orbitDays[i].toInt(),
                    imageResId = imageRes
                )
            }
        }

        /**
         * Tries to find a drawable image based on the planet's name.
         * Example: "Earth" → R.drawable.earth
         */
        private fun getPlanetImageRes(context: Context, planetName: String): Int {
            val imageName = planetName.lowercase().replace(" ", "_")
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }
}
