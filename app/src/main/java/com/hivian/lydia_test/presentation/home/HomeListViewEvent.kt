package com.hivian.lydia_test.presentation.home

import com.hivian.lydia_test.business.model.domain.RandomUserDomain

sealed class HomeListViewEvent {

    data class OpenDetailView(val randomUser: RandomUserDomain) : HomeListViewEvent()

}
