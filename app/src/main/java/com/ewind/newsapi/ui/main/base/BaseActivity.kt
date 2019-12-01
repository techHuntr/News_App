package com.ewind.newsapi.ui.main.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Arafath Misree on 30/11/19.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}