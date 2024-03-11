package ru.ermakov.productsapp.presentation.screen.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.productsapp.R
import ru.ermakov.productsapp.app.ProductsApplication
import ru.ermakov.productsapp.databinding.FragmentProductsBinding
import javax.inject.Inject

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var productsViewModelFactory: ProductsViewModelFactory
    private lateinit var productsViewModel: ProductsViewModel

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
        TODO("Not yet implemented")
    }

    private fun setUpProductRecyclerView() {
        TODO("Not yet implemented")
    }

    private fun setUpListeners() {

    }

    private fun setUpObservers() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}