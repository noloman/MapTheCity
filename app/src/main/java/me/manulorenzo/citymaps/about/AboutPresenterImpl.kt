package me.manulorenzo.citymaps.about

import android.content.Context
import android.os.Handler
import me.manulorenzo.citymaps.about.About.Presenter
import me.manulorenzo.citymaps.about.About.View
import java.lang.ref.WeakReference

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 */

class AboutPresenterImpl(view: View, context: Context) :
    Presenter {
    private val aboutView: WeakReference<View> = WeakReference(view)
    private val aboutModel: AboutModelImpl = AboutModelImpl(this, context)
    override val aboutInfo: Unit
        get() {
            val aboutViewImpl = aboutView.get()
            aboutViewImpl?.showProgress()
            Handler().postDelayed({ aboutModel.aboutInfo }, 1000)
        }

    override fun onSuccess(aboutInfo: AboutInfo?) {
        val aboutViewImpl = aboutView.get()
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress()
            aboutViewImpl.setCompanyName(aboutInfo!!.companyName)
            aboutViewImpl.setCompanyAddress(aboutInfo.companyAddress)
            aboutViewImpl.setCompanyPostalCode(aboutInfo.companyPostal)
            aboutViewImpl.setCompanyCity(aboutInfo.companyCity)
            aboutViewImpl.setAboutInfo(aboutInfo.aboutInfo)
        }
    }

    override fun onFail() {
        val aboutViewImpl = aboutView.get()
        if (aboutViewImpl != null) {
            aboutViewImpl.hideProgress()
            aboutViewImpl.showError()
        }
    }
}