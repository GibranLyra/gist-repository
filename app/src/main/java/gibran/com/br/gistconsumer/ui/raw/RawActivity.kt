package gibran.com.br.gistconsumer.ui.raw

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.setupActionBar
import kotlinx.android.synthetic.main.activity_raw.*
import org.jetbrains.anko.startActivity

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */

internal const val EXTRA_RAW= "EXTRA_RAW"

class RawActivity: AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, raw: String) {
            context.startActivity<RawActivity>(EXTRA_RAW to raw)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raw)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        intent.extras.getString(EXTRA_RAW).let {
            rawTextView.text = it
        }
    }
}