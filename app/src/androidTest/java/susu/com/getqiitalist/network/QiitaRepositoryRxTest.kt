package susu.com.getqiitalist.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import rx.Observable
import rx.observers.TestSubscriber
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.presenter.client.BaseJsonClient
import susu.com.getqiitalist.presenter.repositories.QiitaRepositoryRx
import susu.com.getqiitalist.common.constants.HttpConstants
import susu.com.getqiitalist.model.service.QiitaListService
import java.io.IOException

/**
 * データ取得テスト
 */
@RunWith(AndroidJUnit4::class)
class QiitaRepositoryRxTest : BaseJsonClient(){

    /**
     * Qiita APIからデータ取得
     * 正常系(No Error : エラーなし)
     */
    @Test
    fun getQiitaList(){
        // 通信クライアントのオブジェクト作成
        val observable : Observable<List<QiitaDTO>> = QiitaRepositoryRx()
            .getClient()
            .create(QiitaListService::class.java)
            .getQiitaNote(HttpConstants.PAGE, HttpConstants.PER_PAGE)

        // Testsubscriberを作成する
        val testSubscriber: TestSubscriber<List<QiitaDTO>> = TestSubscriber.create()
        // テストしたいObservableに先程のSubscriberインスタンスをsubscribeに代入
        observable.subscribe(testSubscriber)
        // 処理が完了するのを待ちます
        testSubscriber.awaitTerminalEvent()
        // 検証します
        testSubscriber.assertNoErrors()
        // モックデータと検証します
        testSubscriber.assertValue(mockData())
    }

    /**
     * Qiita APIからデータ取得
     * 異常系(Error : エラーあり)
     * 種別 : HttpException(HTTPエラー)
     */
    @Test
    fun getQiitaListHTTPError(){
        // 通信クライアントのオブジェクト作成
        var observable : Observable<List<QiitaDTO>> = QiitaRepositoryRx()
            .getClient()
            .create(QiitaListService::class.java)
            .getQiitaNote(HttpConstants.PAGE, HttpConstants.PER_PAGE)
            .map {
                /** ここが重要 */
                // 強制的にエラーを発生させる
                throw HttpException::class.java.newInstance()
            }

        // Testsubscriberを作成する
        val testSubscriber: TestSubscriber<List<QiitaDTO>> = TestSubscriber.create()
        // テストしたいObservableに先程のSubscriberインスタンスをsubscribeに代入
        observable.subscribe(testSubscriber)
        // 処理が完了するのを待ちます
        testSubscriber.awaitTerminalEvent()
        // エラーをキャッチ
        observable.doOnError {
            // 検証します
            testSubscriber.assertError(it)
        }
    }

    /**
     * Qiita APIからデータ取得
     * 異常系(Error : エラーあり)
     * 種別 : IOException(ネットワークエラー)
     */
    @Test
    fun getQiitaListNetWorkError(){
        // 通信クライアントのオブジェクト作成
        var observable : Observable<List<QiitaDTO>> = QiitaRepositoryRx()
            .getClient()
            .create(QiitaListService::class.java)
            .getQiitaNote(HttpConstants.PAGE, HttpConstants.PER_PAGE)
            .map {
                /** ここが重要 */
                // 強制的にエラーを発生させる
                throw IOException::class.java.newInstance()
            }

        // Testsubscriberを作成する
        val testSubscriber: TestSubscriber<List<QiitaDTO>> = TestSubscriber.create()
        // テストしたいObservableに先程のSubscriberインスタンスをsubscribeに代入
        observable.subscribe(testSubscriber)
        // 処理が完了するのを待ちます
        testSubscriber.awaitTerminalEvent()
        // エラーをキャッチ
        observable.doOnError {
            // 検証します
            testSubscriber.assertError(it)
        }
    }

    /**
     * Qiita APIからデータ取得
     * 異常系(Error : エラーあり)
     * 種別 : IllegalAccessException(不明なエラー)
     */
    @Test
    fun getQiitaListUnknownError(){
        // 通信クライアントのオブジェクト作成
        var observable : Observable<List<QiitaDTO>> = QiitaRepositoryRx()
            .getClient()
            .create(QiitaListService::class.java)
            .getQiitaNote(HttpConstants.PAGE, HttpConstants.PER_PAGE)
            .map {
                /** ここが重要 */
                // 強制的にエラーを発生させる
                throw IllegalAccessException::class.java.newInstance()
            }

        // Testsubscriberを作成する
        val testSubscriber: TestSubscriber<List<QiitaDTO>> = TestSubscriber.create()
        // テストしたいObservableに先程のSubscriberインスタンスをsubscribeに代入
        observable.subscribe(testSubscriber)
        // 処理が完了するのを待ちます
        testSubscriber.awaitTerminalEvent()
        // エラーをキャッチ
        observable.doOnError {
            // 検証します
            testSubscriber.assertError(it)
        }
    }

    /**
     * Mockデータの作成
     */
    fun mockData() : List<QiitaDTO>{
        var mock : List<QiitaDTO> = listOf(
            QiitaDTO( "76a59e0cf6c93db74bd7", "ぼくの考えた最強のSharedPreferences", 0, 0, "https://qiita.com/susu_susu__/items/76a59e0cf6c93db74bd7"),
            QiitaDTO( "0976fd46b31b95298e78","[備忘録]Kotlinでシングルトンクラスの作成方法", 2, 3, "https://qiita.com/susu_susu__/items/0976fd46b31b95298e78"),
            QiitaDTO( "e94829c93090d07ba593","Python3系のAWS CLIと.NET Core v2.1の入ったCentOSイメージを作る", 0, 0, "https://qiita.com/susu_susu__/items/e94829c93090d07ba593"),
            QiitaDTO( "88af247034049945f65d","[Kotlin] isEmpty(), isBlank()などの違い早見表", 3, 0, "https://qiita.com/susu_susu__/items/88af247034049945f65d"),
            QiitaDTO( "545083dc7b0e7e0c40cc","iOSアプリの設計手法について", 10, 10, "https://qiita.com/susu_susu__/items/545083dc7b0e7e0c40cc"),
            QiitaDTO( "aff3b8cc26cd2d5535f8","WebViewとネイティブのメリットデメリット", 9, 0, "https://qiita.com/susu_susu__/items/aff3b8cc26cd2d5535f8"))
        return mock
    }
}