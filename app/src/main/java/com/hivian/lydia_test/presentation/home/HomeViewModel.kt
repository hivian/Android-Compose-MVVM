package com.hivian.lydia_test.presentation.home

import android.app.Application
import androidx.lifecycle.*
import com.hivian.lydia_test.R
import com.hivian.lydia_test.business.Mapper
import com.hivian.lydia_test.ui.NetworkState
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.lydia_test.business.remote.getRetrofitApiLayer
import com.hivian.lydia_test.business.repository.RandomUsersRepository
import com.hivian.lydia_test.core.SingleLiveData
import com.hivian.lydia_test.core.localization.ILocalizationService
import com.hivian.lydia_test.core.IScrollMoreDelegate
import com.hivian.lydia_test.core.Resource
import com.talentsoft.android.toolkit.core.IoC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application),
    IScrollMoreDelegate {

    companion object {
        const val RESULT_COUNT = 20
    }

    //region - Dependency Injection

    private val localizationService: ILocalizationService
        get() = IoC.resolve()

    //endregion

    //region - Private Members

    private var pageCount = 1

    private val randomUsersRepository = RandomUsersRepository(getApplication(), getRetrofitApiLayer())

    private var isLoadingMore: Boolean = false

    //endregion

    //region - Public Properties

    var title : String = localizationService.localizedString(R.string.home_fragment_title)

    var data : LiveData<List<RandomUserDomain>> =
        Transformations.map(randomUsersRepository.randomsUsersLocal) {
            if (it.isNotEmpty()) {
                pageCount = it.count() / RESULT_COUNT
            }

            Mapper.mapDTOToDomain(it)
        }

    val networkState = MutableLiveData<NetworkState>()

    val clickEvent = SingleLiveData<HomeListViewEvent>()

    var displayErrorMessage: LiveData<Boolean> =
        Transformations.map(data) {
            it.isEmpty() && networkState.value is NetworkState.Error
        }

    //endregion

    //region - Initialization

    init {
        fetchRandomUsers()
    }

    //endregion

    //region - Public Methods

    fun openRandomUserDetail(randomUser: RandomUserDomain) {
        clickEvent.value = HomeListViewEvent.OpenDetailView(randomUser)
    }

    //endregion

    //region - Private Methods

    private fun fetchRandomUsers() = viewModelScope.launch {
        networkState.value = NetworkState.Loading

        when (val resultList = randomUsersRepository.fetchRandomUsers(pageCount, RESULT_COUNT)) {
            is Resource.Success -> networkState.value = NetworkState.Success
            is Resource.Error -> networkState.value = NetworkState.Error(resultList.message)
        }
    }

    //endregion

    override fun loadMore() {
        if (isLoadingMore) return

        isLoadingMore = true
        viewModelScope.launch(Dispatchers.Main) {
            val resultList = randomUsersRepository.fetchRandomUsers(++pageCount, RESULT_COUNT)
            if (resultList is Resource.Error) {
                pageCount--
            }

            isLoadingMore = false
        }
    }
}
