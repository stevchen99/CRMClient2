package com.example.crmclient2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.crmclient2.databinding.FragmentSecondBinding
import com.example.crmclient2.model.TheHistoBack
import com.example.crmclient2.model.TheTaches
import com.example.crmclient2.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var Taches: ArrayList<TheTaches> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val apiInterfaceTaches = ApiService.create().getTaches()

        apiInterfaceTaches.enqueue(
            object : Callback<List<TheTaches>> {
                override fun onResponse(
                    call: Call<List<TheTaches>>,
                    response: Response<List<TheTaches>>
                ) {
                    if (response.body() != null)
                    {
                        Taches = response.body() as ArrayList<TheTaches>
                        val spinner: Spinner = view.findViewById(R.id.spinnerClient)
                        val btn_clickTache: Button = view.findViewById(R.id.btn_save)
                        Taches = response.body() as ArrayList<TheTaches>
                        spinner.adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, Taches) } as SpinnerAdapter
                    }
                }
                override fun onFailure(call: Call<List<TheTaches>>, t: Throwable) {
                    /*Toast.makeText(this, t?.message ?: "empty", Toast.LENGTH_LONG)
                        .show()*/
                }
            }
        )

        binding.btnSave.setOnClickListener {
            val spinner: Spinner = view.findViewById(R.id.spinnerClient)
            var MontantHr: EditText = view.findViewById(R.id.editTextNumber)
            val LeTaches = spinner.getSelectedItem() as TheTaches
            var oHisto = TheHistoBack(LeTaches.IdTache, MontantHr.text.toString().toInt(),0)

            val apiCreate = ApiService.create().postHisto(oHisto)

            apiCreate.enqueue(
                object : Callback<TheHistoBack> {
                    override fun onFailure(call: Call<TheHistoBack>, t: Throwable) {
                        Log.i("", "Failes" )
                    }

                    override fun onResponse(call: Call<TheHistoBack>, response: Response<TheHistoBack>) {
                        val addedUser = response.body()
                        val codeRep = response.code()
                        Log.i("", "post submitted to API." + addedUser)
                        if (response.isSuccessful()) {
                            Log.i("", "post registration to API" + response.body()!!.toString())
                        }
                    }
                }
            )

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}