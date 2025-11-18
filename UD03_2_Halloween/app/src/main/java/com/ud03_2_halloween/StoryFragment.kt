package com.ud03_2_halloween

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val text = StoryFragmentArgs.fromBundle(requireArguments()).storyText
        val title = StoryFragmentArgs.fromBundle(requireArguments()).storyTitle
        val img = StoryFragmentArgs.fromBundle(requireArguments()).img

        val view =  inflater.inflate(R.layout.fragment_story, container, false)
        view.findViewById<TextView>(R.id.storyTitle).text = title
        view.findViewById<TextView>(R.id.storyText).text = text
        view.findViewById<ImageView>(R.id.storyImage).setImageDrawable(ResourcesCompat.getDrawable(resources, img, null))

        return  view
    }

}