package com.ud03_2_halloween

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import kotlin.text.replace
import androidx.navigation.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [StorySelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StorySelectionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun replaceNameInStory(storyID : Int,name : String) : String {
        val story = getString(storyID)
        val changedStory = story.replace("[name]", name)
        return changedStory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_story_selection, container, false)
        val monsterHouseCard = view.findViewById<CardView>(R.id.monsterHouseCard)
        val skinwalkerCard = view.findViewById<CardView>(R.id.skinwalkerCard)

        val name = welcomeFragmentArgs.fromBundle(requireArguments()).name

        monsterHouseCard.setOnClickListener(){
            val text = replaceNameInStory(R.string.monsterHouseStoryContent, name)
            val img = ResourcesCompat.getDrawable(resources, R.drawable.monster_house, null)
            val action = StorySelectionFragmentDirections.actionStorySelectionFragmentToStoryFragment(getString(R.string.monsterHouseStoryTitle),text, R.drawable.monster_house)
            view.findNavController().navigate(action)
        }

        skinwalkerCard.setOnClickListener(){
            val text = replaceNameInStory(R.string.skinwalkerStoryContent, name)
            val action = StorySelectionFragmentDirections.actionStorySelectionFragmentToStoryFragment(getString(R.string.skinwalkerStoryTitle), text, R.drawable.halloween_2)

            view.findNavController().navigate(action)
        }

        return view
    }

}