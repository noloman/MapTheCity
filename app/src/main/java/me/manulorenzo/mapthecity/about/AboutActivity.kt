package me.manulorenzo.mapthecity.about

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import me.manulorenzo.mapthecity.MapTheCityApplication
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.city.CityListViewModelFactory
import me.manulorenzo.mapthecity.data.Resource

class AboutActivity : AppCompatActivity() {
    private val companyName: TextView? by lazy { findViewById<TextView>(R.id.companyName) }
    private val companyAddress: TextView? by lazy { findViewById<TextView>(R.id.companyAdress) }
    private val companyPostal: TextView? by lazy { findViewById<TextView>(R.id.companypostal) }
    private val companyCity: TextView? by lazy { findViewById<TextView>(R.id.companyCity) }
    private val aboutInfoTextView: TextView? by lazy { findViewById<TextView>(R.id.aboutInfo) }
    private val progressBar: ProgressBar? by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private val errorView: android.view.View? by lazy { findViewById<android.view.View?>(R.id.errorView) }
    private val infoContainer: android.view.View? by lazy { findViewById<android.view.View?>(R.id.infoContainer) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val aboutViewModel =
            CityListViewModelFactory((this.application as MapTheCityApplication).repository)
                .create(
                    AboutViewModel::class.java
                )
        aboutViewModel.aboutInfo.observe(this, Observer {
            when (it) {
                is Resource.Loading -> showProgress()
                is Resource.Error<*> -> {
                    hideProgress()
                    showError()
                }
                is Resource.Success<*> -> {
                    hideProgress()
                    setCompanyName(it.data)
                    setCompanyAddress(it.data)
                    setCompanyPostalCode(it.data)
                    setCompanyCity(it.data)
                    setAboutInfo(it.data)
                }
            }
        })
    }

    private fun setCompanyName(aboutInfo: AboutInfo?) {
        infoContainer?.visibility = android.view.View.VISIBLE
        this.companyName?.text = aboutInfo?.companyName
    }

    private fun setCompanyAddress(aboutInfo: AboutInfo?) {
        this.companyAddress?.text = aboutInfo?.companyAddress
    }

    private fun setCompanyPostalCode(aboutInfo: AboutInfo?) {
        companyPostal?.text = aboutInfo?.companyPostal
    }

    private fun setCompanyCity(aboutInfo: AboutInfo?) {
        this.companyCity?.text = aboutInfo?.companyCity
    }

    private fun setAboutInfo(aboutInfo: AboutInfo?) {
        aboutInfoTextView?.text = aboutInfo?.aboutInfo
    }

    private fun showError() {
        errorView?.visibility = android.view.View.VISIBLE
    }

    private fun showProgress() {
        progressBar?.visibility = android.view.View.VISIBLE
    }

    private fun hideProgress() {
        progressBar?.visibility = android.view.View.GONE
    }
}