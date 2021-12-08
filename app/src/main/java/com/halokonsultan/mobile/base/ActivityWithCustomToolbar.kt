package com.halokonsultan.mobile.base

import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.viewbinding.ViewBinding
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.utils.Utils

abstract class ActivityWithCustomToolbar<VB: ViewBinding>: BaseActivity<VB>() {
    protected fun setupSupportActionBar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    protected fun setTitle(title: String) {
        Utils.setTitleTextView(this, title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}