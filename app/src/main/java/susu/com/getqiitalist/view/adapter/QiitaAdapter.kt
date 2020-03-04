package susu.com.getqiitalist.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import susu.com.getqiitalist.R
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.view.fragment.DetailFragment


class QiitaAdapter(private val context: Context, private val fragment: FragmentManager) : BaseAdapter() {
    // 遅延宣言
    lateinit var qiitaList: List<QiitaDTO>
    // Layoutオブジェクト
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val mfragment = fragment

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

        //各TextView
        val qiita_title = view!!.findViewById<TextView>(R.id.qiita_title)
        qiita_title.text = qiitaList[position].title

        view.setOnClickListener {
            val transaction = mfragment.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.container, DetailFragment.getInstance(qiitaList[position].url))
            transaction.commit()
        }

        // 返却
        return view
    }
}
