package susu.com.getqiitalist.util

import android.util.Log
import susu.com.getqiitalist.BuildConfig

/**
 * ログ出力を担うユーティリティクラス
 */
object LogUtils {

    /** ログ出力が可能か否か */
    private val LOG_ENABLE = BuildConfig.DEBUG

    /**
     * Verboseレベルのログを出力する
     */
    fun v(tag: String, message: String?) {
        if (!LOG_ENABLE) {
            return
        }
        Log.v(tag, message ?: "message is null")
    }

    /**
     * Debugレベルのログを出力する
     */
    fun d(tag: String, message: String?) {
        if (!LOG_ENABLE) {
            return
        }
        Log.d(tag, message ?: "message is null")
    }

    /**
     * Infoレベルのログを出力する
     */
    fun i(tag: String, message: String?) {
        if (!LOG_ENABLE) {
            return
        }
        Log.i(tag, message ?: "message is null")
    }

    /**
     * Warmレベルのログを出力する
     */
    fun w(tag: String, message: String?) {
        if (!LOG_ENABLE) {
            return
        }
        Log.w(tag, message ?: "message is null")
    }

    /**
     * Errorレベルのログを出力する
     */
    fun e(tag: String, message: String?) {
        if (!LOG_ENABLE) {
            return
        }
        Log.e(tag, message ?: "message is null")
    }
}