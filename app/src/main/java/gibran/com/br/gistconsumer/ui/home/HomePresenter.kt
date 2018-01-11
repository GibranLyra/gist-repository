package gibran.com.br.gistconsumer.ui.home

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.githubservice.gists.GistsDataSource
import io.reactivex.disposables.Disposable

private const val PAGE_SIZE = 20

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class HomePresenter(private val gistsDataSource: GistsDataSource,
                    private val view: HomeContract.View,
                    private val schedulerProvider: BaseSchedulerProvider) : HomeContract.Presenter {

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

    override fun loadGists(page: Int) {
        when(page) {
            0 -> view.showLoading(true)
            else -> view.showBottomLoading(true)
        }
        view.showError(false)
        view.showErrorNoData(false)
        gistsRequest = gistsDataSource.publicGists(page, PAGE_SIZE)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({
                    when(page) {
                        0 -> view.showLoading(false)
                        else -> view.showBottomLoading(false)
                    }
                })
                .subscribe({ view.showGists(it) },
                        {
                            view.showError(true)
                            when(page) {
                                0 -> view.showErrorNoData(true)
                            }
                        })
    }
}