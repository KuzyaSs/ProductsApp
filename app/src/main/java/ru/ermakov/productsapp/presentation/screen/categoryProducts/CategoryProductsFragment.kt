package ru.ermakov.productsapp.presentation.screen.categoryProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.app.ProductsApplication
import ru.ermakov.productsapp.databinding.FragmentCategoryProductsBinding
import ru.ermakov.productsapp.presentation.Constants.THRESHOLD
import ru.ermakov.productsapp.presentation.adapter.ProductAdapter
import javax.inject.Inject

class CategoryProductsFragment : Fragment() {
    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!
    private val arguments: CategoryProductsFragmentArgs by navArgs()

    @Inject
    lateinit var categoryProductsViewModelFactory: CategoryProductsViewModelFactory
    private lateinit var categoryProductsViewModel: CategoryProductsViewModel

    private var productAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as ProductsApplication).applicationComponent.inject(fragment = this)
        categoryProductsViewModel = ViewModelProvider(
            this,
            categoryProductsViewModelFactory
        )[CategoryProductsViewModel::class.java]
        if (categoryProductsViewModel.categoryProductsUiState.value?.products.isNullOrEmpty()) {
            categoryProductsViewModel.setUpCategoryProductsScreen(category = arguments.category)
        }
        binding.textViewCategory.text = arguments.category
        setUpSwipeRefreshLayout()
        setUpProductRecyclerView()
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

    private fun setUpProductRecyclerView() {
        productAdapter = ProductAdapter(onItemClickListener = { product ->
            navigateToProductDetailsScreen(productId = product.id)
        })
        binding.recyclerViewProducts.adapter = productAdapter
        binding.recyclerViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    categoryProductsViewModel.loadProductPage(category = arguments.category)
                }
            }
        })
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                categoryProductsViewModel.refreshCategoryProductsScreen(category = arguments.category)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) ||
                        recyclerViewProducts.canScrollVertically(-1)
            }
            textViewCategory.setOnClickListener { goBack() }
        }
    }

    private fun setUpObservers() {
        categoryProductsViewModel.categoryProductsUiState.observe(viewLifecycleOwner) { categoryProductsUiState ->
            categoryProductsUiState.apply {
                productAdapter?.submitList(products)
                setUpRefresh(isRefreshingShown = isRefreshing)
                setUpProgressBar(isLoadingShown = products.isEmpty() && isLoading)
                setUpToastErrorMessage(
                    errorMessage = resources.getString(R.string.connection_failure),
                    isErrorMessageShown = isError
                )
                setUpEmptyProductListMessage(isProductListEmptyShown = products.isEmpty() && !isLoading)
            }
        }
    }

    private fun setUpRefresh(isRefreshingShown: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
    }

    private fun setUpProgressBar(isLoadingShown: Boolean) {
        binding.progressBar.isVisible = isLoadingShown
    }

    private fun setUpToastErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            categoryProductsViewModel.clearErrorMessage()
        }
    }

    private fun setUpEmptyProductListMessage(isProductListEmptyShown: Boolean) {
        binding.linearLayoutEmptyListMessage.isVisible = isProductListEmptyShown
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToProductDetailsScreen(productId: Long) {
        val action =
            CategoryProductsFragmentDirections.actionCategoryProductsFragment2ToProductDetailsFragment(
                productId = productId
            )
        findNavController().navigate(action)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}