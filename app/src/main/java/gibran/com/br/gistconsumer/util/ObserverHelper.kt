package gibran.com.br.gistconsumer.util

import io.reactivex.disposables.Disposable


/**
 * Created by gibran.lyra on 22/02/2016.
 */
class ObserverHelper {

    companion object {
        fun safelyDispose(vararg disposables: Disposable) {
            disposables
                    .filter { it.isDisposed }
                    .forEach { it.dispose() }
        }
    }
}
