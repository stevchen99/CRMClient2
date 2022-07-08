package com.example.crmclient2.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi

import com.example.crmclient2.R
import com.example.crmclient2.model.TheHisto
import java.text.SimpleDateFormat

import java.time.LocalDate.*
import java.time.format.DateTimeFormatter

class HistoAdapter(private val context: Context,
                   private  val datasource : ArrayList<TheHisto>) : BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
       return datasource.size
    }

    override fun getItem(position: Int): Any {
     return datasource[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.custom_liste, parent, false)

        val tache = rowView.findViewById(R.id.tachesTxt) as TextView
        val heure = rowView.findViewById(R.id.heureTxt) as TextView
        val date = rowView.findViewById(R.id.dateTxt) as TextView

        val item = getItem(position) as TheHisto

        tache.text = item.Taches
        heure.text = item.HeureAffect.toString()

        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
         date.text =  parse(item.DateAffect, pattern).toString()



        return  rowView
    }
}