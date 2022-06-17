package pro.ply.gerrit.conf

import pro.ply.gerrit.utilities.EncodeHelper
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.util.Properties

object ConfigurationManager {
    private val configurations: Properties = Properties()

    private val configurationChangedObservers = mutableListOf<ConfigurationChangedObserver>()

    fun loadConfigurations() {
        val dir = System.getProperty("user.home")
        val config = File("${dir}/${Configurations.CONFIG_FILENAME}")

        val exists = if (config.exists()) true else config.createNewFile()

        if (!exists) throw FileNotFoundException("Config file can't be created")

        FileReader(config).use {
            configurations.load(it)
        }

        notifyConfigurationChanged()
    }

    fun setAuthorization(username: String, password: String){
        configurations.setProperty("username", username)
        configurations.setProperty("password", password)
        notifyConfigurationChanged()
        saveConfigurations()
    }

    fun getAuthorization(): String {
        val builder = StringBuilder()
            .append(configurations.getProperty("username"))
            .append(":")
            .append(configurations.getProperty("password"))
        return EncodeHelper.base64(builder.toString())
    }

    private fun saveConfigurations() {
        val dir = System.getProperty("user.home")
        val config = File("${dir}/${Configurations.CONFIG_FILENAME}")

        if (!config.exists()) throw FileNotFoundException("Config file can't be created")

        FileWriter(config).use {
            configurations.store(it, null)
        }
    }

    fun addObserver(observer: ConfigurationChangedObserver) {
        configurationChangedObservers.add(observer)
    }

    private fun notifyConfigurationChanged() {
        for (observer in configurationChangedObservers) {
            observer.onChanged()
        }
    }
}
