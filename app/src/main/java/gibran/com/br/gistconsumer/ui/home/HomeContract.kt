package gibran.com.br.gistconsumer.ui.home

import gibran.com.br.gistconsumer.ui.base.BaseContractPresenter
import gibran.com.br.gistconsumer.ui.base.BaseContractView
import gibran.com.br.githubservice.model.Gist

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
public interface HomeContract {
    interface View : BaseContractView<HomeContract.Presenter> {
        fun showLoading(show: Boolean)
        fun showBottomLoading(show: Boolean)
        fun showError(show: Boolean)
        fun showErrorNoData(show: Boolean)
        fun showGists(gists: ArrayList<Gist>)
    }

    interface Presenter : BaseContractPresenter {
        fun loadGists(page: Int)
    }
}

