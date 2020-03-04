package susu.com.getqiitalist

import android.app.Application

class RetrofitApplication : Application() {

    companion object {
        private lateinit var sInstance: Application
        fun getInstance() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}
