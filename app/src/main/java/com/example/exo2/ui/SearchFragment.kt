package com.example.exo2.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.exo2.R
import com.example.exo2.db.Helpers
import com.example.exo2.db.Intervention
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private var dateIntervention:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_interventions!!.setHasFixedSize(true)
        recycler_view_interventions!!.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        arguments?.let {
            dateIntervention = SearchFragmentArgs.fromBundle(it).dateIntervention
        }


            context?.let{
                val interventions = getSomeElments(dateIntervention!!)
                recycler_view_interventions!!.adapter = InterventionAdapter(interventions)
            }

    }

    private fun getSomeElments(dateIntervention:String) :List<Intervention>
    {
        val stream = context?.openFileInput("First.json")
        val jsonFileString = Helpers.lire(stream!!)
        val listInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
        var interventions: ArrayList<Intervention> = Gson().fromJson(jsonFileString, listInterventionType)
        stream.close()

        val events = interventions!!.mapIndexedNotNull { index, event ->  if (event.interventionDate.equals(dateIntervention)) event else null}
        return events
    }


}
