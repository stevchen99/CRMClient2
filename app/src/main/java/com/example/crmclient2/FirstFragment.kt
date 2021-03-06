package com.example.crmclient2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crmclient2.Adapter.HistoAdapter
import com.example.crmclient2.databinding.FragmentFirstBinding
import com.example.crmclient2.model.TheHisto
import com.example.crmclient2.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listView : ListView
    private var Histo : ArrayList<TheHisto> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiInterfaceHisto = ApiService.create().getHisto()
        listView = view.findViewById<ListView>(R.id.HistoList)

        apiInterfaceHisto.enqueue(
            object : Callback<List<TheHisto>> {
                override fun onResponse(
                    call: Call<List<TheHisto>>,
                    response: Response<List<TheHisto>>
                ) {
                    if (response.body() != null)
                    {
                        Histo = response.body() as ArrayList<TheHisto>
                        listView.adapter = activity?.let {HistoAdapter(it, Histo)} as HistoAdapter


                    }
                }

                override fun onFailure(call: Call<List<TheHisto>>, t: Throwable) {
                  /*  Toast.makeText(applicationContext, t?.message ?: "empty", Toast.LENGTH_LONG)
                        .show()*/
                }
            }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}