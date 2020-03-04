package susu.com.getqiitalist.model.cache

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import susu.com.getqiitalist.model.entities.QiitaDTO

class QiitaCache (application: Application) : AndroidViewModel(application) {

    /** 都市情報のリスト */
    val QiitaListLiveData: MutableLiveData<List<QiitaDTO>> = MutableLiveData()

}