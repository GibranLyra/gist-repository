package gibran.com.br.gistconsumer.ui.favorite

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.gistconsumer.util.tests.EspressoIdlingResource
import gibran.com.br.githubservice.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class FavoritePresenter(private val database: MyDatabase,
                        private val view: FavoriteContract.View,
                        private val schedulerProvider: BaseSchedulerProvider) : FavoriteContract.Presenter {

    private var gistsQuery: Disposable? = null

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //Dispose query to avoid memory leaks
        gistsQuery?.let { ObserverHelper.safelyDispose(it) }
    }

    override fun loadFavorites() {
        view.showLoading(true)
        view.showError(false)
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice
        gistsQuery = Observable.just(0)
                .map { database.gistDao().getAll() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({
                    view.showLoading(false)
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                })

                .subscribe({ view.showFavorites(it) }, {
                    Timber.e(it, "loadFavorites: %s", it.message)
                    view.showError(true)
                })
    }
}