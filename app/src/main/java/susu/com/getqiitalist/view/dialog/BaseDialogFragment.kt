package susu.com.getqiitalist.view.dialog

import androidx.fragment.app.DialogFragment

/**
 * Dialogのライフサイクル親クラス
 */
abstract class BaseDialogFragment : DialogFragment() {

    /** ダイアログ表示のフラグ */
    private var mIsDismissDialogReserved = false

    override fun onResume() {
        super.onResume()
        if (mIsDismissDialogReserved) {
            dismiss()
            mIsDismissDialogReserved = false
        }
    }

    override fun dismiss() {
        if (isResumed) {
            super.dismiss()
        } else {
            mIsDismissDialogReserved = true
        }
    }
}