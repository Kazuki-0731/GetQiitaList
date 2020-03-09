package susu.com.getqiitalist.view.activity

import androidx.appcompat.app.AppCompatActivity
import susu.com.getqiitalist.R
import susu.com.getqiitalist.presenter.exception.RetrofitException
import susu.com.getqiitalist.common.util.LogUtils
import susu.com.getqiitalist.view.dialog.BaseDialogFragment
import susu.com.getqiitalist.view.dialog.TextDialogFragment

/**
 * 全てのアクティビティの親クラス
 */
abstract class BaseActivity : AppCompatActivity() {

    /** ダイアログ表示のフラグ */
    private var mIsShowDialogReserved = false
    /** 表示予定のダイアログ */
    private var mReservedDialog: BaseDialogFragment? = null

    /**
     * アプリ復帰時
     */
    override fun onResume() {
        super.onResume()
        if (mIsShowDialogReserved and (mReservedDialog != null)) {
            showDialog(mReservedDialog)
            mIsShowDialogReserved = false
            mReservedDialog = null
        }
    }

    /**
     * ダイアログを表示する
     * @param dialogFragment ダイアログ
     */
    fun showDialog(dialogFragment: BaseDialogFragment?) {

        try {
            dialogFragment?.show(supportFragmentManager, null)
        } catch (e: IllegalStateException) {
            LogUtils.d("debug", "Error : ".plus(e.message))
            mIsShowDialogReserved = true
            mReservedDialog = dialogFragment
        }
    }

    /**
     * 通信エラーダイアログを表示する
     * @param throwable エラーオブジェクト
     */
    fun showHttpErrorDialog(throwable: RetrofitException) {
        val message = when (throwable.mErrorType) {
            RetrofitException.ErrorType.HTTP -> getString(R.string.dialog_message_http_error)
            RetrofitException.ErrorType.NETWORK -> getString(R.string.dialog_message_network_error)
            RetrofitException.ErrorType.UNEXPECTED -> getString(R.string.dialog_message_unexpected_error)
        }
        showDialog(TextDialogFragment(mMessage = message))

    }
}