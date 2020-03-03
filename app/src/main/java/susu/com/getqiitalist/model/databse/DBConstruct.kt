package susu.com.getqiitalist.model.databse

import android.provider.BaseColumns

// DBの情報管理
object DBConstruct {

    // テーブル情報
    class DataEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "qiita_table"
            const val ID = "id"
            const val TITLE = "title"
        }
    }
}
