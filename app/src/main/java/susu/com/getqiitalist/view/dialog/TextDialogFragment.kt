package susu.com.getqiitalist.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import susu.com.getqiitalist.R

class TextDialogFragment(
    /** ダイアログのタイトル */
    private val mTitle: String? = null,
    /** ダイアログのメッセージ */
    private val mMessage: String,
    /** OKボタンの文言 */
    private val positiveButtonLabel: Int = R.string.dialog_button_positive,
    /** OKボタンの押下時のリスナ */
    private val positiveButtonListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() },
    /** キャンセルボタンの文言 */
    private val negativeButtonLabel: Int = R.string.dialog_button_negative,
    /** キャンセルボタンの押下時のリスナ */
    private val negativeButtonListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
) : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).apply {
            if (mTitle != null) {
                setTitle(mTitle)
            }
            setMessage(mMessage)

            setPositiveButton(positiveButtonLabel, positiveButtonListener)
            setNegativeButton(negativeButtonLabel, negativeButtonListener)
        }.create()
    }
}