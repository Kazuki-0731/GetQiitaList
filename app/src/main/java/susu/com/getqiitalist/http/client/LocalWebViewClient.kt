package susu.com.getqiitalist.http.client

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

/**
 * WebViewClientを継承
 *
 * WebViewが読み込みされるタイミング
 * WebViewが失敗するタイミングを検知させる
 */
class LocalWebViewClient(private val activity: Activity) : WebViewClient() {

    /* 読み込み終了 */
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        // ローディングを非表示
        activity.progressBar.visibility = View.GONE
    }

    /* 読み込み失敗 */
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        // ローディングを非表示
        activity.progressBar.visibility = View.GONE

        // TODO : 時間があったら、ダイアログ表示するか、404ページを表示させる
    }
}
