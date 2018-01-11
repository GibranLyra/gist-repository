package gibran.com.br.gistconsumer.ui.gistdetail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.net.nowonline.presentation.util.schedulers.SchedulerProvider
import gibran.com.br.gistconsumer.AppContext
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.replaceFragmentInActivity
import gibran.com.br.gistconsumer.ui.setupActionBar
import gibran.com.br.githubservice.gists.GistsApi
import org.jetbrains.anko.intentFor

class GistDetailActivity : AppCompatActivity() {

    private var gistId: String? = null

    companion object {
        fun createIntent(context: Context, gistId: String, transitionView: View) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, transitionView,
                    "author_image_transition")
            context.startActivity(context.intentFor<GistDetailActivity>().putExtra(EXTRA_GIST_ID, gistId), options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gist_detail)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        initData()
        openGistDetailFragment()
    }

    private fun initData() {
        intent.extras.getString(EXTRA_GIST_ID).let {
            gistId = intent.extras.getString(EXTRA_GIST_ID)
        }
    }


    private fun openGistDetailFragment() {
        gistId?.let {
            val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as GistDetailFragment? ?: GistDetailFragment.newInstance(it).also {
                replaceFragmentInActivity(it, R.id.contentFrame)
            }
            GistDetailPresenter(GistsApi, AppContext.instance.database, fragment, SchedulerProvider)
        } ?: run {
            throw RuntimeException("Gist id cannot be null.")
        }
    }
}
