package ru.ermakov.productsapp.presentation.screen.productDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.app.ProductsApplication
import ru.ermakov.productsapp.databinding.FragmentProductDetailsBinding
import ru.ermakov.productsapp.domain.model.Product
import ru.ermakov.productsapp.presentation.adapter.SliderImageAdapter
import javax.inject.Inject

class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val arguments: ProductDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var productDetailsViewModelFactory: ProductDetailsViewModelFactory
    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    private var sliderImageAdapter: SliderImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as ProductsApplication).applicationComponent.inject(fragment = this)
        productDetailsViewModel = ViewModelProvider(
            this,
            productDetailsViewModelFactory
        )[ProductDetailsViewModel::class.java]
        if (productDetailsViewModel.productDetailsUiState.value?.product == null) {
            productDetailsViewModel.setUpProductDetailsScreen(productId = arguments.productId)
        }
        setUpSwipeRefreshLayout()
        setUpImageViewPager()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                resources.getColor(R.color.backgroundColor)
            )
            setColorSchemeColors(
                resources.getColor(R.color.iconTint)
            )
        }
    }

    private fun setUpImageViewPager() {
        sliderImageAdapter = SliderImageAdapter()
        binding.viewPagerImages.adapter = sliderImageAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                productDetailsViewModel.refreshProductDetails(productId = arguments.productId)
            }
            textViewBrandWithCategory.setOnClickListener { goBack() }
        }
    }

    private fun setUpObservers() {
        productDetailsViewModel.productDetailsUiState.observe(viewLifecycleOwner) { productDetailsUiState ->
            productDetailsUiState.apply {
                if (product != null) {
                    setUpProductDetails(product = product)
                    setUpToastErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessage
                    )
                }
                setUpLoadingScreen(isLoadingScreenShown = product == null)
                setUpRefresh(isRefreshingShown = isRefreshing)
                setUpProgressBar(isLoadingShown = product == null && isLoading)
                setUpErrorMessage(isErrorMessageShown = isErrorMessage)
            }
        }
    }

    private fun setUpProductDetails(product: Product) {
        binding.apply {
            textViewBrandWithCategory.text = resources.getString(
                R.string.brand_with_category,
                product.brand,
                product.category
            )
            sliderImageAdapter?.submitList(product.images)
            textViewTitle.text = product.title
            textViewDescription.text = product.description
            textViewPrice.text = resources.getString(R.string.price_with_currency, product.price)
            textViewRating.text = product.rating.toString()
        }
    }

    private fun setUpLoadingScreen(isLoadingScreenShown: Boolean) {
        binding.viewLoadingScreen.isVisible = isLoadingScreenShown
    }

    private fun setUpRefresh(isRefreshingShown: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
    }

    private fun setUpProgressBar(isLoadingShown: Boolean) {
        binding.progressBar.isVisible = isLoadingShown
    }

    private fun setUpErrorMessage(isErrorMessageShown: Boolean) {
        binding.linearLayoutErrorMessage.isVisible = isErrorMessageShown
    }

    private fun setUpToastErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            productDetailsViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}