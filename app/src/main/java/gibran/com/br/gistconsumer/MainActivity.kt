package gibran.com.br.gistconsumer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import gibran.com.br.githubservice.gists.GistsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GistsApi.publicGists(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("It: %s", it.toString()) },
                        { Timber.e(it, "Error: %s", it.message); })

    }
}
