package gibran.com.br.gistconsumer.ui.base

interface BaseContractView<in T> {

    fun isActive(): Boolean

    fun setPresenter(presenter: T)

}
