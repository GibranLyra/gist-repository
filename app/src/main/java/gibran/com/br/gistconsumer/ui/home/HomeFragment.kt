package gibran.com.br.gistconsumer.ui.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.gistdetail.GistDetailActivity
import gibran.com.br.gistconsumer.ui.recycler.gist.GistAdapter
import gibran.com.br.gistconsumer.ui.showSnackBar
import gibran.com.br.githubservice.model.Gist
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var presenter: HomeContract.Presenter

    private var hasLoaded = false
    private var page = 0

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
            presenter.loadGists(page)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            presenter.loadGists(0)
        }
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> loadingProgressBar.show()
            else -> loadingProgressBar.hide()
        }
    }

    override fun showError(show: Boolean) {
        when (show) {
            true -> view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_LONG,
                    getString(R.string.try_again), { presenter.loadGists(page) })
        }
    }

    override fun showGists(gists: List<Gist>) {
        gistsRecycler.adapter?.let {
            (gistsRecycler.adapter as GistAdapter).add(gists.toMutableList())
        } ?: run {
            hasLoaded = true
            setupRecycler(gists)
        }
    }

    private fun setupRecycler(gists: List<Gist>) {
        val linearLayoutManager = LinearLayoutManager(context)
        gistsRecycler.layoutManager = linearLayoutManager
        gistsRecycler.adapter = GistAdapter(gists.toMutableList()) {
            context?.let { context -> GistDetailActivity.createIntent(context, it.id) }
        }
        gistsRecycler.setHasFixedSize(true)
        gistsRecycler.addOnScrolledToEnd {
            page = it
            presenter.loadGists(page)
        }
    }
}

fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: (Int) -> Unit) {

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private val VISIBLE_THRESHOLD = 5

        private var loading = true
        private var previousTotal = 0
        private var page = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            with(layoutManager as LinearLayoutManager) {
                val visibleItemCount = childCount
                val totalItemCount = itemCount
                val firstVisibleItem = findFirstVisibleItemPosition()
                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                    page++
                    onScrolledToEnd(page)
                    loading = true
                }
            }
        }
    })
}

