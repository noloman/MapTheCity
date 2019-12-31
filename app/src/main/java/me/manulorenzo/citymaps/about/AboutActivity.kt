package me.manulorenzo.citymaps.about

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.manulorenzo.citymaps.CityMapsApplication
import me.manulorenzo.citymaps.R
import me.manulorenzo.citymaps.about.About.View

class AboutActivity : AppCompatActivity(), View {
    private val companyName: TextView? by lazy { findViewById<TextView>(R.id.companyName) }
    private val companyAddress: TextView? by lazy { findViewById<TextView>(R.id.companyAdress) }
    private val companyPostal: TextView? by lazy { findViewById<TextView>(R.id.companypostal) }
    private val companyCity: TextView? by lazy { findViewById<TextView>(R.id.companyCity) }
    private val aboutInfo: TextView? by lazy { findViewById<TextView>(R.id.aboutInfo) }
    private val progressBar: ProgressBar? by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private val errorView: android.view.View? by lazy { findViewById<android.view.View?>(R.id.errorView) }
    private val infoContainer: android.view.View? by lazy { findViewById<android.view.View?>(R.id.infoContainer) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aboutPresenter =
            AboutPresenterImpl(this, (this.application as CityMapsApplication).repository)
        aboutPresenter.aboutInfo
    }

    override fun setCompanyName(companyName: String?) {
        infoContainer?.visibility = android.view.View.VISIBLE
        this.companyName?.text = companyName
    }

    override fun setCompanyAddress(companyAddress: String?) {
        this.companyAddress?.text = companyAddress
    }

    override fun setCompanyPostalCode(postalCode: String??) {
        companyPostal?.text = postalCode
    }

    override fun setCompanyCity(companyCity: String?) {
        this.companyCity?.text = companyCity
    }

    override fun setAboutInfo(info: String?) {
        aboutInfo?.text = info
    }

    override fun showError() {
        errorView?.visibility = android.view.View.VISIBLE
    }

    override fun showProgress() {
        progressBar?.visibility = android.view.View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = android.view.View.GONE
    }
}