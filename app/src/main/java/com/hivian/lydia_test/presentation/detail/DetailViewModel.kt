package com.hivian.lydia_test.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hivian.lydia_test.R
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.lydia_test.core.services.base.ViewModelBase
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.talentsoft.android.toolkit.core.IoC

class DetailViewModel(randomUser: RandomUserDomain): ViewModelBase() {

    private val localizationService: ILocalizationService
        get() = IoC.resolve()

    val title = MutableLiveData(localizationService.localizedString(R.string.detail_fragment_title))

    val picture = MutableLiveData(randomUser.picture)

    val name = MutableLiveData(randomUser.fullName)

    val email = MutableLiveData(randomUser.email)

    val cell = MutableLiveData(randomUser.cell)

    val phone = MutableLiveData(randomUser.phone)

}
