package go.kr.mapo.mapoyouth.ui.youth

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import go.kr.mapo.mapoyouth.network.MapoYouthService
import go.kr.mapo.mapoyouth.network.repository.YouthDataSource
import go.kr.mapo.mapoyouth.network.repository.YouthRepository
import go.kr.mapo.mapoyouth.network.repository.YouthSearchResult
import go.kr.mapo.mapoyouth.network.response.YouthDetails
import go.kr.mapo.mapoyouth.util.PAGE_SIZE
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-09-14
 * @desc
 */

@HiltViewModel
class YouthViewModel @Inject constructor(
    private val mapoYouthService: MapoYouthService,
    private val youthRepository: YouthRepository) : ViewModel() {

    val youthList = Pager(PagingConfig(PAGE_SIZE, prefetchDistance = 1, maxSize = 100)) {
        YouthDataSource(mapoYouthService, null)
    }.liveData.cachedIn(viewModelScope)

    private val _youthDetails = MutableLiveData<YouthDetails>()
    val youthDetails : LiveData<YouthDetails> = _youthDetails

    private val _state = MutableLiveData(false)
    val state : LiveData<Boolean> = _state

    fun setYouthDetails(id: Int) {
        _state.value = false
        viewModelScope.launch {
            youthRepository.getYouthDetails(id)?.let {
                _youthDetails.value = it.value
                _state.value = true
            }
        }
    }

    private val keyword = MutableLiveData("")
    val youthSearchResult = keyword.switchMap {
        Pager(PagingConfig(PAGE_SIZE, maxSize = 100)) {
            YouthDataSource(mapoYouthService, it)
        }.liveData.cachedIn(viewModelScope)
    }

    fun requestSearchYouth(keyword: String) {
        this.keyword.value = keyword
    }

}