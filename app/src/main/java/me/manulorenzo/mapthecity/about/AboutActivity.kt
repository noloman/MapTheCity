package me.manulorenzo.mapthecity.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_about.aboutInfo
import kotlinx.android.synthetic.main.activity_about.companyAdress
import kotlinx.android.synthetic.main.activity_about.companyCity
import kotlinx.android.synthetic.main.activity_about.companyName
import kotlinx.android.synthetic.main.activity_about.companypostal
import kotlinx.android.synthetic.main.activity_about.errorView
import kotlinx.android.synthetic.main.activity_about.infoContainer
import kotlinx.android.synthetic.main.activity_about.progressBar
import me.manulorenzo.mapthecity.MapTheCityApplication
import me.manulorenzo.mapthecity.R
import me.manulorenzo.mapthecity.city.CityListViewModelFactory
import me.manulorenzo.mapthecity.data.Resource

class AboutActivity : AppCompatActivity() {
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
        companyName?.text = aboutInfo?.companyName
    }

    private fun setCompanyAddress(aboutInfo: AboutInfo?) {
        companyAdress.text = aboutInfo?.companyAddress
    }

    private fun setCompanyPostalCode(aboutInfo: AboutInfo?) {
        companypostal.text = aboutInfo?.companyPostal
    }

    private fun setCompanyCity(aboutInfo: AboutInfo?) {
        companyCity?.text = aboutInfo?.companyCity
    }

    private fun setAboutInfo(aboutInfo: AboutInfo?) {
        this.aboutInfo.text = aboutInfo?.aboutInfo
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