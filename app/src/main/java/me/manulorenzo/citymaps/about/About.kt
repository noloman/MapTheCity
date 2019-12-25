package me.manulorenzo.citymaps.about

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * MVP contract for AboutActivity
 */

interface About {
    interface Model {
        val aboutInfo: Unit
    }

    interface Presenter {
        val aboutInfo: Unit
        fun onSuccess(aboutInfo: AboutInfo?)
        fun onFail()
    }

    interface View {
        fun setCompanyName(companyName: String?)
        fun setCompanyAddress(companyAddress: String?)
        fun setCompanyPostalCode(postalCode: String?)
        fun setCompanyCity(companyCity: String?)
        fun setAboutInfo(info: String?)
        fun showError()
        fun showProgress()
        fun hideProgress()
    }
}