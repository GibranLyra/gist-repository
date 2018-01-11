package gibran.com.br.gistconsumer.ui.favorite

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.AppContext
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.gists.GistsDataSource
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

private const val PAGE_SIZE = 20

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class FavoritePresenter(private val gistsDataSource: GistsDataSource,
                        private val view: FavoriteContract.View,
                        private val schedulerProvider: BaseSchedulerProvider) : FavoriteContract.Presenter {

    private var gistsRequest: Disposable? = null

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //Dispose request to avoid memory leaks
        gistsRequest?.let { ObserverHelper.safelyDispose(it) }
    }

    override fun loadFavorites() {
        view.showLoading(true)
        view.showError(false)
        Observable.just(0)
                .subscribeOn(schedulerProvider.io())
                .map { AppContext.instance.database.gistDao().getAll() }
                .doOnTerminate({
                    view.showLoading(false)
                })
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.showFavorites(it)
                }, { view.showError(true) })
    }
}