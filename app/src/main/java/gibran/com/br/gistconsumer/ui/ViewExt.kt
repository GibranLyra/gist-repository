package gibran.com.br.gistconsumer.ui

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(message: String, duration: Int, actionText: String, actionAction: () -> Unit) {
    Snackbar.make(this, message, duration)
            .setAction(actionText, { actionAction() })
            .show()
}