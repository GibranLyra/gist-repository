package gibran.com.br.gistconsumer.ui.gistdetail

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.gists.GistsApi
import io.reactivex.disposables.Disposable

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class GistDetailPresenter(private val gistsApi: GistsApi,
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
}