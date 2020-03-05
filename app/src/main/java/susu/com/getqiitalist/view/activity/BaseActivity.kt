package susu.com.getqiitalist.view.activity

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import susu.com.getqiitalist.R
import susu.com.getqiitalist.http.exception.RetrofitException
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
     *
     * @param dialogFragment ダイアログ
     */
    fun showDialog(dialogFragment: BaseDialogFragment?) {

        try {
            dialogFragment?.show(supportFragmentManager, null)
        } catch (e: IllegalStateException) {
            Log.d("debug", "Error : ".plus(e.message))
            mIsShowDialogReserved = true
            mReservedDialog = dialogFragment
        }
    }

    /**
     * 通信エラーダイアログを表示する
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