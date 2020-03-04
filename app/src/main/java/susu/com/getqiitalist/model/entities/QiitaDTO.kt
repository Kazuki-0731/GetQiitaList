package susu.com.getqiitalist.model.entities

import com.squareup.moshi.Json

data class QiitaDTO (
    @Json(name = "id") // 記事ID
    val id: String,
    @Json(name = "title") // タイトル
    val title: String,
    @Json(name = "likes_count") // お気に入り数
    val likes_count: Int,
    @Json(name = "reactions_count") // 返信数
    val reactions_count: Int,
    @Json(name = "url") // 記事のURL
    val url: String
)
