package susu.com.getqiitalist.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import susu.com.getqiitalist.R
import susu.com.getqiitalist.RetrofitApplication
import susu.com.getqiitalist.controller.repository.ItemRepository
import susu.com.getqiitalist.http.client.ApiClientManager
import susu.com.getqiitalist.http.exception.RetrofitException
import susu.com.getqiitalist.model.cache.QiitaCache
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.view.activity.BaseActivity
import susu.com.getqiitalist.view.activity.MainActivity
import susu.com.getqiitalist.view.adapter.QiitaAdapter
import susu.com.getqiitalist.view.dialog.BaseDialogFragment
import susu.com.getqiitalist.view.dialog.TextDialogFragment

/**
 * ListViewのFragment
 */
class QiitaFragment : BaseFragment() {

    var dataList: List<QiitaDTO> = mutableListOf()

    private val qiitaViewModel: QiitaCache by lazy {
        ViewModelProvider.AndroidViewModelFactory(RetrofitApplication.getInstance())
            .create(QiitaCache::class.java)
    }

    // 取得するページ数など
    private var PAGE = 1
    private var PAR_PAGE = 20

    // 静的領域
    companion object {
        // 遅延宣言
        private var instance : QiitaFragment = QiitaFragment()
        // シングルトンなインスタンス取得
        fun getInstance(): QiitaFragment {
            return instance
        }
    }

    // リストAdapterのメンバ変数
    private var adapter : QiitaAdapter? = null

    // region Fragment()拡張
    // View生成時
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /**
         * 再描画防止(onCreateは1度しか呼ばれない)
         * 横画面に回転する度にFragmentが呼ばれてしまうため
         */
        retainInstance = true
        return inflater.inflate(R.layout.content_main, container, false)
    }

    // View生成後後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ローディングを表示
        activity!!.progressBar.visibility = View.VISIBLE

        // データ取得
        getQiitaData()
        // Viewの表示処理
        setupQiitaList()

        // スワイプ時の処理
        swiperefresh.setOnRefreshListener {
            getQiitaData()
        }
    }
    //endregion

    /**
     * Qiitaのリスト表示をするレイアウトを構築する
     */
    private fun setupQiitaList() {
        // Adapter生成
        adapter = QiitaAdapter(activity!!.applicationContext, fragmentManager!!)
        // 初期値格納
        adapter!!.qiitaList = dataList
        // listViewに代入
        listView.adapter = adapter
        // 長押しイベント付与
        listView.setOnItemLongClickListener { parent, view, position, id ->
            // 対象セルの文字列表示
            val listView: ListView = parent as ListView
            val str = listView.getItemAtPosition(position) as String
            Toast.makeText(activity!!.applicationContext, "$str", Toast.LENGTH_LONG).show()
            true
        }

        /**
         * 本当はobserve()使って、受信時の処理を細かく記述しようとしたが、時間の関係上あと回し
         */
        // region TODO : あとで保守
        // オブザーブ受信後の表示側
//        qiitaViewModel.QiitaListLiveData.observe(viewLifecycleOwner, Observer {
//            adapter!!.qiitaList = it
//            // notify??
//            adapter!!.notifyDataSetChanged()
//        })
        // endregion
    }

    /**
     * Qiita記事のタイトル一覧を取得する
     */
    private fun getQiitaData() {
        // Retrofitでデータ取得
//        val itemRepository = ItemRepository(activity!!, this)
//        itemRepository.getItemList { itemList ->
//            if(activity!!.progressBar.visibility == View.VISIBLE){
//                // ローディングを非表示
//                activity!!.progressBar.visibility = View.GONE
//            }
//            // ロードアイコン非表示
//            swiperefresh.isRefreshing = false
//            // Listをadapterにセット
//            adapter!!.qiitaList = itemList
//            // リロード
//            adapter!!.notifyDataSetChanged()
//        }

        // region TODO : あとで保守
        mCompositeDisposable.add(
            ApiClientManager.apiClient.itemsRx(page = PAGE, perPage = PAR_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d("debug", "rx response=$it")
                    if(activity!!.progressBar.visibility == View.VISIBLE){
                        // ローディングを非表示
                        activity!!.progressBar.visibility = View.GONE
                    }
                    // ロードアイコン非表示
                    swiperefresh.isRefreshing = false
                    // Listをadapterにセット
                    adapter!!.qiitaList = it
                    // リロード
                    adapter!!.notifyDataSetChanged()
                }
                .doOnError {
                    // エラー種別振り分け
                    val exception = RetrofitException.asRetrofitException(it!!)
                    // ダイアログ表示
                    (activity!! as? BaseActivity)?.showHttpErrorDialog(exception)
                }
                .doOnCompleted {
                    // ロードアイコン非表示
                    stopSwipeLoadIcon()
                }
                .subscribe()
        )

//        mCompositeDisposable.add(
//            QiitaClient().getQiitaNote(
//                { qiita ->
//                    // 通信後の処理
//                    val qiitaList = mutableListOf<QiitaDTO>()
//                    qiitaViewModel.QiitaListLiveData.postValue(qiitaList)
//                    qiitaList.forEach {
//                        Log.d("debug", "title : ".plus(it.title))
//                    }
//                },
//                { throwable ->
//                    if (throwable is RetrofitException) {
//                        (activity as? BaseActivity)?.showHttpErrorDialog(throwable)
//                    }
//                }
//            )
//        )
        // endregion
    }

    /**
     * スワイプ時のアイコン非表示
     */
    fun stopSwipeLoadIcon(){
        // ローディングを非表示
        activity!!.progressBar.visibility = View.GONE
        // ロードアイコン非表示
        swiperefresh.isRefreshing = false
    }
}
