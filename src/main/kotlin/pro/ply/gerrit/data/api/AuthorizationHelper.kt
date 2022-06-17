package pro.ply.gerrit.data.api

import pro.ply.gerrit.conf.ConfigurationChangedObserver
import pro.ply.gerrit.conf.ConfigurationManager

object AuthorizationHelper: ConfigurationChangedObserver {
    private var passwordAuthentication: String = ""

    fun passwordAuthorization(): String {
        if (passwordAuthentication.isEmpty()) {
            refreshPassword()
        }

        return passwordAuthentication
    }

    private fun refreshPassword() {
        passwordAuthentication = ConfigurationManager.getAuthorization()
    }


    override fun onChanged() {
        refreshPassword()
    }
}
