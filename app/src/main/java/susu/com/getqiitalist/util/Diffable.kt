package susu.com.getqiitalist.util

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtilのためのクラス・関数群
 */
interface Diffable {

    /** otherと同じIDを持つかどうか */
    fun isTheSame(other: Diffable): Boolean = equals(other)

    /** ohterと完全に一致しているかどうか */
    fun isContentsTheSame(other: Diffable): Boolean = equals(other)
}

private class Callback(
    val old: List<Diffable>,
    val new: List<Diffable>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size
    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].isTheSame(new[newItemPosition])
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].isContentsTheSame(new[newItemPosition])
    }
}

/**
 * 新旧のリストの差異を計算する
 */
fun calculateDiff(
    old: List<Diffable>,
    new: List<Diffable>,
    detectMoves: Boolean = false
): DiffUtil.DiffResult {
    return DiffUtil.calculateDiff(Callback(old, new), detectMoves)
}