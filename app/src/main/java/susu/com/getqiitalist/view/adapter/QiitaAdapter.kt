package susu.com.getqiitalist.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import rx.subscriptions.CompositeSubscription
import susu.com.getqiitalist.R
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.model.api.repositories.QiitaRepositoryRx
import susu.com.getqiitalist.model.api.exception.RetrofitException
import susu.com.getqiitalist.common.util.LogUtils
import susu.com.getqiitalist.view.activity.BaseActivity
import susu.com.getqiitalist.view.fragment.DetailFragment

/**
 * Qiita記事一覧表示するListViewのアダプター
 */
class QiitaAdapter(private val activity: Activity, private val fragment: FragmentManager) : BaseAdapter() {
    // 表示させるためのList
    lateinit var qiitaList: List<QiitaDTO>
    // Layoutオブジェクト
    private val layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val mfragment = fragment
    private val compositeDisposable = CompositeSubscription()

    override fun getCount(): Int {
        return qiitaList.count()
    }

    override fun getItem(position: Int): QiitaDTO {
        return qiitaList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(convertView == null){
            // 表示するレイアウト取得
            view = layoutInflater.inflate(R.layout.qiita_list_item, parent, false)
        }

        // お気に入り
        val favorite = view!!.findViewById<TextView>(R.id.favorite)
        favorite.text = "いいね数 : ".plus(qiitaList[position].likes_count)

        // 返信数
        val comments_count = view!!.findViewById<TextView>(R.id.comments_count)
        comments_count.text = "返信数 : ".plus(qiitaList[position].comments_count)

        // 閲覧数
        val page_views_count = view!!.findViewById<TextView>(R.id.page_views_count)
        page_views_count.text = "閲覧数 : ".plus(
            if(qiitaList[position].page_views_count.toString().isNullOrBlank())
                "0"
            else
                qiitaList[position].page_views_count.toString()
        )

        // Qiita APIより閲覧数を取得
        //https://qiita.com/api/v2/items/id
        compositeDisposable.add(
            // Qiitaの記事一覧取得
            QiitaRepositoryRx().getQiitaNote(
                qiitaList[position].id,
                { qiita ->
                    // 通信後の処理
                    LogUtils.d("debug", "rx response = $qiita")
                    page_views_count.text = "閲覧数 : ".plus(
                        if(qiita.page_views_count.toString().isNullOrBlank())
                            "0"
                        else
                            qiita.page_views_count.toString()
                    )
                },
                { throwable ->
                    // 例外発生時
                    // エラー種別振り分け
                    val exception = RetrofitException.asRetrofitException(throwable)
                    // ダイアログ表示
                    (activity as? BaseActivity)?.showHttpErrorDialog(exception)
                }
            )
        )

        //各TextView
        val qiita_title = view!!.findViewById<TextView>(R.id.qiita_title)
        qiita_title.text = qiitaList[position].title

        // ListViewのセル押下時
        view.setOnClickListener {
            // 詳細画面へ遷移
            val transaction = mfragment.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.container, DetailFragment.getInstance(qiitaList[position].url))
            transaction.commit()
        }

        // 返却
        return view
    }
}
