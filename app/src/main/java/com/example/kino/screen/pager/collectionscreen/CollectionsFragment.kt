package com.example.kino.screen.pager.collectionscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.kino.NavigationUi
import com.example.kino.NavigationUi.*
import com.example.kino.network.model.movie.BelongsToCollections
import com.example.kino.screen.common.BaseFragment

class CollectionsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(belongsToCollections: BelongsToCollections) : CollectionsFragment =
            CollectionsFragment().apply {
                arguments = bundleOf(BELONGS_TO_COLLECTIONS.name to belongsToCollections)
            }
    }
}