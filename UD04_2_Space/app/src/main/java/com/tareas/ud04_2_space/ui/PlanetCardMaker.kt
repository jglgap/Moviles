package com.tareas.ud04_2_space.ui
package com.example.planets.ui

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.tareas.ud04_2_space.R

class PlanetCardMaker(private val context: Context) {

        fun createCard(planet: Planet): MaterialCardView {
            // --- Outer Card ---
            val card = MaterialCardView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(dp(8), dp(8), dp(8), dp(8))
                }
                radius = dp(12).toFloat()
                cardElevation = dp(4).toFloat()
                useCompatPadding = true
                id = View.generateViewId()
            }

            // --- Main Vertical Layout ---
            val mainLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // --- Image ---
            val image = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(194)
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
                contentDescription = "Image of ${planet.name}"
                id = View.generateViewId()
                planet.imageResId?.let { setImageResource(it) }
            }

            // --- Text container ---
            val textContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(16), dp(16), dp(16), dp(16))
            }

            val title = TextView(context).apply {
                text = planet.name
                setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_HeadlineSmall)
            }

            val secondaryText = TextView(context).apply {
                text = "Distance from Sun: ${planet.distanceFromSun} million km"
                setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
            }

            val supportingText = TextView(context).apply {
                text = """
                Orbit time: ${planet.orbitDays} days
                Atmosphere: ${planet.atmosphere}
                Info: ${planet.info}
            """.trimIndent()
                setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodySmall)
            }

            textContainer.addView(title)
            textContainer.addView(secondaryText)
            textContainer.addView(supportingText)

            // --- Buttons container ---
            val buttons = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(dp(8), dp(8), dp(8), dp(16))
            }

            val action1 = MaterialButton(context, null, com.google.android.material.R.attr.borderlessButtonStyle).apply {
                text = "Learn More"
                id = View.generateViewId()
            }

            val action2 = MaterialButton(context, null, com.google.android.material.R.attr.borderlessButtonStyle).apply {
                text = "Share"
                id = View.generateViewId()
            }

            buttons.addView(action1)
            buttons.addView(action2)

            // --- Assemble all parts ---
            mainLayout.addView(image)
            mainLayout.addView(textContainer)
            mainLayout.addView(buttons)
            card.addView(mainLayout)

            return card
        }

        private fun dp(value: Int): Int =
            (value * context.resources.displayMetrics.density).toInt()
    }
}