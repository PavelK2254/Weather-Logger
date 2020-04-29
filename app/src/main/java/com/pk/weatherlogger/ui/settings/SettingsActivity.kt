package com.pk.weatherlogger.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pk.weatherlogger.R
import com.pk.weatherlogger.utils.Constants

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)
            val preference: Preference? = findPreference("CLEAR_DATA")
            preference?.setOnPreferenceClickListener {
                val returnIntent = Intent()
                returnIntent.putExtra("wipeDB",true)
                this.requireActivity().setResult(Activity.RESULT_OK,returnIntent)
                this.requireActivity().finish()
                true
            }
        }
    }


}