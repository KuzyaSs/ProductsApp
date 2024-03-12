package ru.ermakov.productsapp.presentation.screen.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.app.ProductsApplication
import ru.ermakov.productsapp.databinding.FragmentProductsBinding
import ru.ermakov.productsapp.presentation.adapter.CategoryAdapter
import ru.ermakov.productsapp.presentation.adapter.ProductAdapter
import javax.inject.Inject

private const val THRESHOLD = 6

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var productsViewModelFactory: ProductsViewModelFactory
    private lateinit var productsViewModel: ProductsViewModel

    private var categoryAdapter: CategoryAdapter? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as ProductsApplication).applicationComponent.inject(fragment = this)
        productsViewModel = ViewModelProvider(
            this,
            productsViewModelFactory
        )[ProductsViewModel::class.java]
        setUpSwipeRefreshLayout()
        setUpCategoryRecyclerView()
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

    private fun setUpCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(onItemClickListener = { category ->
            navigateToCategoryProductsScreen(category = category)
        })
        binding.recyclerViewCategories.adapter = categoryAdapter
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
                    productsViewModel.loadProductPage()
                }
            }
        })
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                productsViewModel.refreshProductsScreen()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) ||
                        recyclerViewProducts.canScrollVertically(-1)
            }
            imageViewSearch.setOnClickListener { navigateToSearchProductsScreen() }
        }
    }

    private fun setUpObservers() {
        productsViewModel.productsUiState.observe(viewLifecycleOwner) { productsUiState ->
            productsUiState.apply {
                categoryAdapter?.submitList(categories)
                productAdapter?.submitList(products)
                setUpRefresh(isRefreshingShown = isRefreshing)
                setUpProgressBar(isLoadingShown = products.isEmpty() && isLoading)
                setUpToastErrorMessage(
                    errorMessage = errorMessage,
                    isErrorMessageShown = isErrorMessage
                )
                setUpEmptyProductListMessage(isProductListEmpty = products.isEmpty() && !isLoading)
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
            productsViewModel.clearErrorMessage()
        }
    }

    private fun setUpEmptyProductListMessage(isProductListEmpty: Boolean) {
        binding.linearLayoutEmptyListMessage.isVisible = isProductListEmpty
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSearchProductsScreen() {
        val action = ProductsFragmentDirections.actionProductsFragmentToSearchProductsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToCategoryProductsScreen(category: String) {
        val action = ProductsFragmentDirections.actionProductsFragmentToCategoryProductsFragment2(
            category = category
        )
        findNavController().navigate(action)
    }

    private fun navigateToProductDetailsScreen(productId: Long) {
        val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
            productId = productId
        )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}