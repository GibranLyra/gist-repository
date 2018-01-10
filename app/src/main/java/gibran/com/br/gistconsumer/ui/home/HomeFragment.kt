package gibran.com.br.gistconsumer.ui.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.showSnackBar
import gibran.com.br.githubservice.model.Gist
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var presenter: HomeContract.Presenter
    private var hasLoaded = false

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            //TODO get list from bundle
            presenter.loadGists()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            presenter.loadGists()
        }
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(show: Boolean) {
        when {
            show -> loadingProgressBar.show()
            else -> loadingProgressBar.hide()
        }
    }

    override fun showError(show: Boolean) {
        when {
            show -> view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_LONG,
                    getString(R.string.try_again), { presenter.loadGists() })
        }
    }

    override fun showGists(gists: List<Gist>) {
        hasLoaded = true
    }
}