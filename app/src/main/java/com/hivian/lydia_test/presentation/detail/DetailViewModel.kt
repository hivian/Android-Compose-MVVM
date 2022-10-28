package com.hivian.lydia_test.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hivian.lydia_test.R
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.common.localization.ILocalizationService
import com.talentsoft.android.toolkit.core.IoC

class DetailViewModel(randomUser: RandomUserDomain): ViewModel() {

    //region - Dependency Injection

    private val localizationService: com.hivian.common.localization.ILocalizationService
        get() = IoC.resolve()

    //endregion

    val title: String = localizationService.localizedString(R.string.detail_fragment_title)

    val data = MutableLiveData(randomUser)

}
