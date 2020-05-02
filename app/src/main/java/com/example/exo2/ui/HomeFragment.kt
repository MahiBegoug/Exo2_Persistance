package com.example.exo2.ui


import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.exo2.R
import com.example.exo2.db.Helpers
import com.example.exo2.db.Intervention
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var mDateListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_interventions!!.setHasFixedSize(true)
        recycler_view_interventions!!.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

//        val jsonFileString = getJsonDataFromAsset(requireContext(), "First.json")
//        Log.i("data", jsonFileString)
        val stream = context?.openFileInput("First.json")
        val jsonFileString = Helpers.lire(stream!!)
        val gson = Gson()
        val listInterventionType = object : TypeToken<List<Intervention>>() {}.type
        var interventions: List<Intervention> = gson.fromJson(jsonFileString, listInterventionType)
        stream.close()
        recycler_view_interventions!!.adapter = InterventionAdapter(interventions)


        btn_add.setOnClickListener {
            val action = HomeFragmentDirections.actionAddIntervention()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> showdateOfSearch()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showdateOfSearch()
    {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateListener, year, month, day
        )

        picker.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        picker.show()

        mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var cal = Calendar.getInstance()
            cal.set(year, month, day)
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            //Log.i("time",sdf.format(cal.time))
            val dateIntervention = sdf.format(cal.time)
            Log.i("dateIntervention", dateIntervention)

            val action = HomeFragmentDirections.actionSearchIntervention(dateIntervention)
            Navigation.findNavController(requireView()).navigate(action)
        }

}











}
