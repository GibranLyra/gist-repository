package gibran.com.br.gistconsumer

import br.com.net.nowonline.presentation.util.schedulers.ImmediateSchedulerProvider
import gibran.com.br.gistconsumer.ui.home.HomeContract
import gibran.com.br.gistconsumer.ui.home.HomePresenter
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by gibranlyra on 11/01/18 for gist_consumer.
 */
class HomePresenterUnitTest {
    @Mock private lateinit var gistApi: GistMockedService
    @Mock private lateinit var viewContract: HomeContract.View

    private lateinit var schedulerProvider: ImmediateSchedulerProvider
    private lateinit var presenterContract: HomeContract.Presenter

    @Before
    fun setupHomePresenter() {
        MockitoAnnotations.initMocks(this)
        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        // Get a reference to the class under test
        presenterContract = HomePresenter(gistApi, viewContract, schedulerProvider)

        // The presenterContract won't update the viewContract unless it's active.
        `when`(viewContract.isActive()).thenReturn(true)
    }

    @Test
    @Throws(Exception::class)
    fun loadGistsAndLoadIntoView_Success() {
        `when`(gistApi.publicGists(any(Int::class.java), any(Int::class.java)))
                .thenReturn(Observable.just(GistMockedService.GISTS))
        presenterContract.loadGists(0)

        val inOrder = inOrder(viewContract)

        inOrder.verify(viewContract).setPresenter(presenterContract)
        inOrder.verify(viewContract).showLoading(true)
        inOrder.verify(viewContract).showError(false)
        inOrder.verify(viewContract).showErrorNoData(false)
        inOrder.verify(viewContract).showGists(ArgumentMatchers.anyList())
        inOrder.verify(viewContract).showLoading(false)
        inOrder.verify(viewContract, never()).showError(true)
        inOrder.verify(viewContract, never()).showErrorNoData(true)
    }

    @Test
    @Throws(Exception::class)
    fun loadGistsAndLoadIntoView_Error() {
        `when`(gistApi.publicGists(any(Int::class.java), any(Int::class.java)))
                .thenReturn(Observable.error(Exception("This is a error test.")))
        presenterContract.loadGists(0)

        val inOrder = inOrder(viewContract)
        inOrder.verify(viewContract).setPresenter(presenterContract)
        inOrder.verify(viewContract).showLoading(true)
        inOrder.verify(viewContract).showError(false)
        inOrder.verify(viewContract).showErrorNoData(false)
        inOrder.verify(viewContract).showLoading(false)
        inOrder.verify(viewContract).showError(true)
        inOrder.verify(viewContract).showErrorNoData(true)
        verify(viewContract, never()).showGists(ArgumentMatchers.anyList())
    }


}