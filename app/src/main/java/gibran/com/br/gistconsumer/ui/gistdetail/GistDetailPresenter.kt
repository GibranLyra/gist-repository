package gibran.com.br.gistconsumer.ui.gistdetail

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.gists.GistsApi
import gibran.com.br.githubservice.model.Gist
import gibran.com.br.githubservice.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class GistDetailPresenter(private val gistsApi: GistsApi,
                          private val database: MyDatabase,
                          private val view: GistDetailContract.View,
                          private val schedulerProvider: BaseSchedulerProvider) : GistDetailContract.Presenter {
    private var gistRequest: Disposable? = null

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //Dispose request to avoid memory leaks
        gistRequest?.let { ObserverHelper.safelyDispose(it) }
    }

    override fun loadGist(gistId: String) {
        view.showLoading(true)
        view.showError(false)
        gistRequest = gistsApi.gist(gistId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({ view.showLoading(false) })
                .subscribe({ view.showGist(it) },
                        { view.showError(true) })

    }

    override fun saveFavorite(gist: Gist) {
        Observable.just(0)
                .map { database.gistDao().insert(gist) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ view.favoriteSaved() }, {
                    Timber.e(it, "saveFavorite: %s", it.message)
                    view.saveFavoriteError()
                })
    }

    override fun checkFavorite(gistId: String) {
        Observable.just(0)
                .map { database.gistDao().findById(gistId) != null }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ view.isFavorite(it) }, {
                    Timber.e(it, "checkFavorite: %s", it.message)
                    view.showError(true)
                })
    }
}
