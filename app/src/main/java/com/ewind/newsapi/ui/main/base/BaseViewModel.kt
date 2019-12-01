package com.ewind.newsapi.ui.main.base

import androidx.lifecycle.ViewModel

/**
 * Created by Arafath Misree on 30/11/19.
 */

abstract class BaseViewModel : ViewModel() {

    abstract fun start()

    abstract fun stop()
}