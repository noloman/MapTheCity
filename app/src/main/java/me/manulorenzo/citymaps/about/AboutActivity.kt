package me.manulorenzo.citymaps.about

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.manulorenzo.citymaps.R
import me.manulorenzo.citymaps.about.About.View

class AboutActivity : AppCompatActivity(), View {
    private var companyName: TextView? = null
    private var companyAddress: TextView? = null
    private var companyPostal: TextView? = null
    private var companyCity: TextView? = null
    private var aboutInfo: TextView? = null
    private var progressBar: ProgressBar? = null
    private var errorView: android.view.View? = null
    private var infoContainer: android.view.View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aboutPresenter =
            AboutPresenterImpl(this, this)
        companyName = findViewById(R.id.companyName)
        companyAddress = findViewById(R.id.companyAdress)
        companyPostal = findViewById(R.id.companypostal)
        companyCity = findViewById(R.id.companyCity)
        aboutInfo = findViewById(R.id.aboutInfo)
        progressBar = findViewById(R.id.progressBar)
        errorView = findViewById(R.id.errorView)
        infoContainer = findViewById(R.id.infoContainer)
        // TODO
//        aboutPresenter.getAboutInfo()
    }

    override fun setCompanyName(companyNameString: String?) {
        infoContainer!!.visibility = android.view.View.VISIBLE
        companyName!!.text = companyNameString
    }

    override fun setCompanyAddress(companyAddressString: String?) {
        companyAddress!!.text = companyAddressString
    }

    override fun setCompanyPostalCode(postalCodeString: String??) {
        companyPostal!!.text = postalCodeString
    }

    override fun setCompanyCity(companyCityString: String?) {
        companyCity!!.text = companyCityString
    }

    override fun setAboutInfo(infoString: String?) {
        aboutInfo!!.text = infoString
    }

    override fun showError() {
        errorView!!.visibility = android.view.View.VISIBLE
    }

    override fun showProgress() {
        progressBar!!.visibility = android.view.View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = android.view.View.GONE
    }
}