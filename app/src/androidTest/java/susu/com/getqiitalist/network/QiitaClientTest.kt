package susu.com.getqiitalist.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import susu.com.getqiitalist.presenter.client.QiitaClient
import susu.com.getqiitalist.view.fragment.QiitaFragment

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class QiitaClientTest {

    /**
     * Qiita APIからデータ取得
     * 正常系
     */
    @Test
    fun getQiitaList(){
        // 宣言
        val qiitaFragment = QiitaFragment()
        val method = QiitaFragment::class.java.getDeclaredMethod("getQiitaData")
        // privateメソッドに対して外部からアクセス許可
        method.isAccessible = true
        // 実行
        method.invoke(qiitaFragment)
    }

    /**
     * Qiita APIからデータ取得
     * 異常系
     * 種別 : HTTPエラー
     */
    @Test
    fun getQiitaListHTTPEror(){
        // 宣言
        val qiitaClient = QiitaClient()
        val method = QiitaClient::class.java.getDeclaredMethod("getQiitaNote")
        // privateメソッドに対して外部からアクセス許可
        method.isAccessible = true
        // 実行
        method.invoke(qiitaClient)
    }

}