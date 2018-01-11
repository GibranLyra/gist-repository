package gibran.com.br.gistconsumer.ui.home

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.gists.GistsApi
import io.reactivex.disposables.Disposable

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class HomePresenter(private val gistsApi: GistsApi,
                    private val view: HomeContract.View,
                    private val schedulerProvider: BaseSchedulerProvider) : HomeContract.Presenter {

    private var gistsRequest: Disposable? = null
    private var times = 0
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

    override fun loadGists() {
        view.showLoading(true)
        view.showError(false)
        gistsRequest = gistsApi.publicGists(times, 20)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({ view.showLoading(false) })
                .subscribe({ view.showGists(it) },
                        { view.showError(true) })
        times++
    }
}