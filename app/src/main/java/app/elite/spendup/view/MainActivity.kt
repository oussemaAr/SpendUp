package app.elite.spendup.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import app.elite.spendup.R
import app.elite.spendup.databinding.ActivityMainBinding
import app.elite.spendup.worker.SeedDatabaseWorker
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniMenu()
        val seedRequest: WorkRequest = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
            .build()
        WorkManager
            .getInstance(this)
            .enqueue(seedRequest)
    }

    private fun iniMenu() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
            navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.dashboardFragment -> {
                        supportActionBar!!.setDisplayShowTitleEnabled(false)
                    }
                    R.id.addTransactionFragment -> {
                        supportActionBar!!.setDisplayShowTitleEnabled(true)
                        binding.toolbar.title = "Add Transaction"
                    }
                    R.id.addTagFragment ->
                        supportActionBar!!.setDisplayShowTitleEnabled(false)
                    else -> {
                        supportActionBar!!.setDisplayShowTitleEnabled(true)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}