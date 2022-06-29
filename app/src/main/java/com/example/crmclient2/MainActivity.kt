package com.example.crmclient2

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.crmclient2.Adapter.HistoAdapter
import com.example.crmclient2.databinding.ActivityMainBinding
import com.example.crmclient2.model.TheHisto
import com.example.crmclient2.model.TheTaches
import com.example.crmclient2.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var listView : ListView
    private var Histo : ArrayList<TheHisto> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val apiInterfaceHisto = ApiService.create().getHisto()
        listView = findViewById<ListView>(R.id.HistoList)

        apiInterfaceHisto.enqueue(
            object : Callback<List<TheHisto>> {
                override fun onResponse(
                    call: Call<List<TheHisto>>,
                    response: Response<List<TheHisto>>
                ) {
                    if (response.body() != null)
                    {
                        Histo = response.body() as ArrayList<TheHisto>
                        listView.adapter = HistoAdapter(this@MainActivity, Histo)
                    }
                }

                override fun onFailure(call: Call<List<TheHisto>>, t: Throwable) {
                    Toast.makeText(applicationContext, t?.message ?: "empty", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )







    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}