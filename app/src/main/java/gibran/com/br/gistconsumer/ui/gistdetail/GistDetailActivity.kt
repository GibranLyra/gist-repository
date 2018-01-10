package gibran.com.br.gistconsumer.ui.gistdetail

import android.content.Context

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.net.nowonline.presentation.util.schedulers.SchedulerProvider
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.replaceFragmentInActivity
import gibran.com.br.gistconsumer.ui.setupActionBar
import gibran.com.br.githubservice.gists.GistsApi
import org.jetbrains.anko.startActivity

class GistDetailActivity : AppCompatActivity() {

    private var gistId: String? = null

    companion object {
        fun createIntent(context: Context, gistId: String) {
            context.startActivity<GistDetailActivity>(EXTRA_GIST_ID to gistId)
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
            GistDetailPresenter(GistsApi, fragment, SchedulerProvider)
        } ?: run {
            throw RuntimeException("Gist id cannot be null.")
        }
    }
}
