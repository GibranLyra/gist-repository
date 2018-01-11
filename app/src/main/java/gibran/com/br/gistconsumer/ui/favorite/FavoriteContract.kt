package gibran.com.br.gistconsumer.ui.favorite

import gibran.com.br.gistconsumer.ui.base.BaseContractPresenter
import gibran.com.br.gistconsumer.ui.base.BaseContractView
import gibran.com.br.githubservice.model.Gist

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
public interface FavoriteContract {
    interface View : BaseContractView<FavoriteContract.Presenter> {
        fun showLoading(show: Boolean)
        fun showError(show: Boolean)
        fun showFavorites(gists: List<Gist>)
    }

    interface Presenter : BaseContractPresenter {
        fun loadFavorites()
    }
}

