package com.example.exo2.db

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream
import java.io.IOException

object Helpers {

    @Throws(IOException::class)
    fun lire(stream: FileInputStream): String {
        var n: Int
        val fileContent = StringBuffer("")
        val buffer = ByteArray(1024)
        n = stream.read(buffer)
        while (n != -1) {
            fileContent.append(String(buffer, 0, n))
            n = stream.read(buffer)
        }
        val text = fileContent.toString()
        stream.close()
        return text

    }


     fun saveInterventionToJson(context:Context,mIntervention:Intervention)
    {
        writeInJsonFile(context,readFromJsonFile(context),mIntervention)
    }

    fun updateInterventionToJson(context:Context,mIntervention:Intervention)
    {
        val stream = context.openFileInput("First.json")
        val jsonFileString = Helpers.lire(stream!!)
        val listInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
        var interventions: ArrayList<Intervention> = Gson().fromJson(jsonFileString, listInterventionType)
        stream.close()

        val indices = interventions!!.mapIndexedNotNull { index, event ->  if (event.id.equals(mIntervention.id)) index else null}
        interventions.set(indices[0],mIntervention)

        val jsonString:String = Gson().toJson(interventions)
        val stream1 = context.openFileOutput("First.json", Context.MODE_PRIVATE)
        stream1.write(jsonString.toByteArray())
        stream1.close()

    }

    fun deleteIntervention(context:Context,intervention:Intervention)
    {
        val stream = context?.openFileInput("First.json")
        val jsonFileString = Helpers.lire(stream!!)
        val listInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
        var interventions: ArrayList<Intervention> = Gson().fromJson(jsonFileString, listInterventionType)
        stream.close()

        interventions.remove(intervention)

        val jsonString:String = Gson().toJson(interventions)
        val stream1 = context?.openFileOutput("First.json", Context.MODE_PRIVATE)
        stream1?.write(jsonString.toByteArray())
        stream1?.close()
    }



     fun readFromJsonFile(context: Context) : ArrayList<Intervention>
    {
        val stream = context.openFileInput("First.json")
        val jsonFileString = Helpers.lire(stream!!)
        val listInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
        var interventions: ArrayList<Intervention> = Gson().fromJson(jsonFileString, listInterventionType)
        stream.close()
        return interventions
    }



     fun writeInJsonFile(context: Context,interventions : ArrayList<Intervention>,mIntervention:Intervention)
    {
        interventions.add(mIntervention)
        val jsonString:String = Gson().toJson(interventions)
        val stream = context.openFileOutput("First.json", Context.MODE_PRIVATE)
        stream.write(jsonString.toByteArray())
        stream.close()
    }


}