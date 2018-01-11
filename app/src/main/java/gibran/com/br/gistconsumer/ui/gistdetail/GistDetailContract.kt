package gibran.com.br.gistconsumer.ui.gistdetail

import gibran.com.br.gistconsumer.ui.base.BaseContractPresenter
import gibran.com.br.gistconsumer.ui.base.BaseContractView
import gibran.com.br.githubservice.model.Gist

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
public interface GistDetailContract {
    interface View : BaseContractView<GistDetailContract.Presenter> {
        fun showLoading(show: Boolean)
        fun showError(show: Boolean)
        fun showGist(gist: Gist)
        fun favoriteSaved()
        fun saveFavoriteError()
        fun isFavorite(isFavorite: Boolean)
    }

    interface Presenter : BaseContractPresenter {
        fun loadGist(gistId: String)
        fun saveFavorite(gist: Gist)
        fun checkFavorite(gistId: String)
    }
}

