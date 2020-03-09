package susu.com.getqiitalist.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import rx.subscriptions.CompositeSubscription

/**
 * 全てのフラグメントの親クラス
 */
abstract class BaseFragment : Fragment() {

    // 定期実行用のオブジェクトをまとめて実行するオブジェクト
    // RxJava 1系
//    protected var mCompositeDisposable = CompositeDisposable()
    // RxJava 2系
    protected var mCompositeDisposable = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // スレッドがバッティングしないように初期化
        mCompositeDisposable.clear()
    }

    override fun onDestroy() {
        // スレッドがバッティングしないように初期化
        mCompositeDisposable.clear()
        super.onDestroy()
    }
}
