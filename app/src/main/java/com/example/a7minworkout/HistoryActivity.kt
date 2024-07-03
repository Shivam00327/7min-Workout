package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.a7minworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)

    }
    private fun getAllCompletedDates(historyDao: HistoryDao) {

        lifecycleScope.launch {
            historyDao.fetchALlDates().collect { allCompletedDatesList->

                if (allCompletedDatesList.isNotEmpty()) {
                    binding.tvHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoDataAvailable.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    // History adapter is initialized and the list is passed in the param.
                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter = HistoryAdapter(ArrayList(dates))

                    // Access the RecyclerView Adapter and load the data into it
                    binding.rvHistory.adapter = historyAdapter
                } else {
                    binding.tvHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoDataAvailable.visibility = View.VISIBLE
                }


            }
        }
    }
}