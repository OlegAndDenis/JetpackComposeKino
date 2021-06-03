package com.example.kino.screen.pager.companyscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.kino.NavigationUi
import com.example.kino.NavigationUi.*
import com.example.kino.network.model.movie.BelongsToCollections
import com.example.kino.network.model.movie.ProductionCompanies
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.pager.collectionscreen.CollectionsFragment

class CompanyFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {
        fun newInstance(productionCompanies: List<ProductionCompanies>) : CompanyFragment =
            CompanyFragment().apply {
                arguments = bundleOf(LIST_PRODUCT_COMPANY.name to productionCompanies)
            }
    }
}