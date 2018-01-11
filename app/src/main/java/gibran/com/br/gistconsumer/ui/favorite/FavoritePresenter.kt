package gibran.com.br.gistconsumer.ui.favorite

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

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
        gistsQuery =Observable.just(0)
                .subscribeOn(schedulerProvider.io())
                .map { database.gistDao().getAll() }
                .doOnTerminate({
                    view.showLoading(false)
                })
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.showFavorites(it)
                }, { view.showError(true) })
    }
}