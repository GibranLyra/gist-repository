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

private const val GISTS_RESULT = "gistsResult"
private const val HAS_LOADED = "hasLoaded"

class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var presenter: HomeContract.Presenter

    private var hasLoaded = false
    private var page = 0
    private var gists: ArrayList<Gist>? = null

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            hasLoaded = savedInstanceState.getBoolean(HAS_LOADED, false)
            when (hasLoaded) {
                true -> {
                    gists = savedInstanceState.getParcelableArrayList(GISTS_RESULT)
                    gists?.let { showGists(it) }
                }
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            favoritesRecycler.adapter?.let {
                (it as GistAdapter).clear()
            }
            presenter.loadGists(0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            presenter.loadGists(0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(GISTS_RESULT, gists)
        outState.putBoolean(HAS_LOADED, hasLoaded)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> {
                loadingProgressBar?.show()
                swipeRefreshLayout.isRefreshing = true
            }
            else -> {
                loadingProgressBar?.hide()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun showBottomLoading(show: Boolean) {
        when (show) {
            true -> bottomProgressBar.show()
            else -> bottomProgressBar.hide()
        }
    }

    override fun showError(show: Boolean) {
        when (show) {
            true -> view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_LONG,
                    getString(R.string.try_again), { presenter.loadGists(page) })
        }
    }

    override fun showErrorNoData(show: Boolean) {
        when (show) {
            true -> errorView.visibility = View.VISIBLE
            else -> errorView.visibility = View.GONE
        }
    }

    override fun showGists(gists: ArrayList<Gist>) {
        favoritesRecycler.adapter?.let {
            (it as GistAdapter).add(gists)
        } ?: run {
            hasLoaded = true
            setupRecycler(gists)
        }
    }

    private fun setupRecycler(gists: ArrayList<Gist>) {
        this.gists = gists
        val linearLayoutManager = LinearLayoutManager(context)
        favoritesRecycler.layoutManager = linearLayoutManager
        favoritesRecycler.adapter = GistAdapter(gists.toMutableList()) { gist, view ->
            context?.let { context -> GistDetailActivity.createIntent(context, gist.id, view) }
        }
        favoritesRecycler.setHasFixedSize(true)
        favoritesRecycler.addOnScrolledToEnd {
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

