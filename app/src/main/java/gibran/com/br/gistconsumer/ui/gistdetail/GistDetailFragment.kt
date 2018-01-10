package gibran.com.br.gistconsumer.ui.gistdetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.GlideApp
import gibran.com.br.gistconsumer.ui.showSnackBar
import gibran.com.br.githubservice.model.Gist
import kotlinx.android.synthetic.main.fragment_gist_detail.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */

internal const val EXTRA_GIST_ID = "EXTRA_GIST_ID"

class GistDetailFragment : Fragment(), GistDetailContract.View {
    private lateinit var presenter: GistDetailContract.Presenter
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
            //TODO get list from bundle
            gistId?.let { presenter.loadGist(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            gistId?.let { presenter.loadGist(it) }
        }
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: GistDetailContract.Presenter) {
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
                    getString(R.string.try_again), { gistId?.let { presenter.loadGist(it) } })
        }
    }

    override fun showGist(gist: Gist) {
        gistDescription.text = gist.description
        gistAuthor.text = gist.owner?.login
        gistLanguage.text = gist.files?.file?.toString()
        GlideApp.with(this)
                .load(gist.owner?.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.notification_template_icon_bg)
                .into(authorImage)
    }
}