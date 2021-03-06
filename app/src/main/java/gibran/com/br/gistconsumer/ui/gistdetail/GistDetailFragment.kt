package gibran.com.br.gistconsumer.ui.gistdetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.GlideApp
import gibran.com.br.gistconsumer.ui.raw.RawActivity
import gibran.com.br.gistconsumer.ui.showSnackBar
import gibran.com.br.githubservice.model.Gist
import kotlinx.android.synthetic.main.fragment_gist_detail.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */

internal const val EXTRA_GIST_ID = "EXTRA_GIST_ID"
private const val GIST_RESULT = "gistResult"
private const val HAS_LOADED = "hasLoaded"

class GistDetailFragment : Fragment(), GistDetailContract.View {
    private lateinit var presenter: GistDetailContract.Presenter

    private lateinit var gist: Gist

    private var gistId: String? = null
    private var hasLoaded = false

    companion object {
        fun newInstance(gistId: String): GistDetailFragment {
            val fragment = GistDetailFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_GIST_ID, gistId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gistId = arguments?.getString(EXTRA_GIST_ID)
        return inflater.inflate(R.layout.fragment_gist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            hasLoaded = savedInstanceState.getBoolean(HAS_LOADED, false)
            when (hasLoaded) {
                true -> {
                    gist = savedInstanceState.getParcelable(GIST_RESULT) as Gist
                    showGist(gist)
                }
            }
        }
        openRawButton.setOnClickListener({
            gist.files?.file?.content?.let {
                context?.let { context -> RawActivity.createIntent(context, it) }
            } ?: run {
                view.showSnackBar(getString(R.string.no_raw_to_display), Snackbar.LENGTH_SHORT)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            gistId?.let { presenter.loadGist(it) }
            gistId?.let { presenter.checkFavorite(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GIST_RESULT, gist)
        outState.putBoolean(HAS_LOADED, hasLoaded)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: GistDetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> {
                openRawButton.visibility = View.GONE
                loadingProgressBar.show()
            }
            else -> {
                openRawButton.visibility = View.VISIBLE
                loadingProgressBar.hide()
            }
        }
    }

    override fun showError(show: Boolean) {
        when (show) {
            true -> {
                view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.try_again), { gistId?.let { presenter.loadGist(it) } })
                errorView.visibility = View.VISIBLE
            }
            else -> errorView.visibility = View.GONE
        }
    }

    override fun showGist(gist: Gist) {
        hasLoaded = true
        this.gist = gist
        gistDescription.text = gist.description
        gistAuthor.text = gist.owner?.login
        gistLanguage.text = gist.files?.file?.language
        GlideApp.with(this)
                .load(gist.owner?.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.github_placeholder)
                .into(authorImage)
    }

    override fun favoriteSaved() = isFavorite(true)

    override fun saveFavoriteError() {
        view?.showSnackBar(getString(R.string.save_favorite_error), Snackbar.LENGTH_LONG,
                getString(R.string.try_again), { gist.let { presenter.saveFavorite(it) } })
    }

    override fun favoriteRemoved() = isFavorite(false)

    override fun removedFavoriteError() {
        view?.showSnackBar(getString(R.string.remove_favorite_message), Snackbar.LENGTH_LONG,
                getString(R.string.try_again), { gist.let { presenter.removeFavorite(it) } })
    }

    override fun isFavorite(isFavorite: Boolean) {
        when (isFavorite) {
            true -> {
                favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
                favoriteButton.setOnClickListener({
                    gist.let { gist ->
                        presenter.removeFavorite(gist)
                    }
                })
            }
            else -> {
                favoriteButton.setImageResource(R.drawable.ic_favorite)
                favoriteButton.setOnClickListener({
                    gist?.let { gist ->
                        presenter.saveFavorite(gist)
                    }
                })
            }
        }
    }
}
