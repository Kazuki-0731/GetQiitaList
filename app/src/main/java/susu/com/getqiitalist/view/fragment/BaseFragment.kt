package susu.com.getqiitalist.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * 全てのフラグメントの親クラス
 */
abstract class BaseFragment : Fragment() {

    protected val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable.clear()
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }
}
