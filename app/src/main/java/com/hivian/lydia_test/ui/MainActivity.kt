package com.hivian.lydia_test.ui

import android.os.Bundle
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.base.ActivityBase
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.databinding.ActivityMainBinding

class MainActivity : ActivityBase<ActivityMainBinding>(R.layout.activity_main) {

    private val navigationService: INavigationService
        get() = IoC.resolve()

    override fun onCreate(savedInstanceState: Bundle?) {
        navigationService.navigationActivity = this

        super.onCreate(savedInstanceState)
    }

}
