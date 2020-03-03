package susu.com.getqiitalist.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.content_main.*
import susu.com.getqiitalist.R
import susu.com.getqiitalist.controller.repository.ItemRepository
import susu.com.getqiitalist.view.util.QiitaAdapter

/**
 * ListViewのFragment
 */
class QiitaFragment : Fragment() {

    // 静的領域
    companion object {
        // 遅延宣言
        private var instance: QiitaFragment =
            QiitaFragment()
        // シングルトンなインスタンス取得
        fun getInstance(): QiitaFragment {
            return instance
        }
    }

    // リストAdapterのメンバ変数
    private var adapter : QiitaAdapter? = null

    // View生成前
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /**
         * 再描画防止(onCreateは1度しか呼ばれない)
         * 横画面に回転した際に何度もFragmentが呼ばれてしまうため
         */
        retainInstance = true
        return inflater.inflate(R.layout.content_main, container, false)
    }

    // 表示後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 配列初期化
        var dataArray: Array<String> = arrayOf("test", "test1")
//        // DB初期化
//        val dbhelper = DBHelper(activity!!.applicationContext)
//        // DBから
//        if(dbhelper.getMaxID() != 0){
//            dataArray = dbhelper.selectTODO()
//        }

        // データ取得
        val itemRepository = ItemRepository()
        itemRepository.getItemList { itemList ->
            itemList.forEach {
                Log.d("test", "title : ".plus(it.title))
            }
            Log.d("test", "$itemList")
        }

        // Adapter生成
        adapter = QiitaAdapter(activity!!.applicationContext, dataArray)
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
    }

    /**
     * DBから取得してリロード
     */
//    fun reload(context: Context, db : DBHelper){
//        // DBから取得
//        var dataArray = db.selectTODO()
//
//        // adapterセット
//        adapter = QiitaAdapter(context, dataArray)
//        listView.adapter = adapter
//        adapter!!.notifyDataSetChanged()
//    }
}
