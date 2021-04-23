package com.example.kino.screen.serialfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.example.kino.CommonFactory
import com.example.kino.screen.common.ZoomOutPageTransformer
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.screen.common.GenresViewHolder
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.MovieAndSerialsFragmentBinding
import com.example.kino.db.model.Genres
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.serial.Serials
import com.example.kino.screen.common.SingleActivity

class SerialFragment : BaseFragment() {

    private val mViewModel: SerialViewModel by viewModels { CommonFactory }

    private lateinit var mBinding: MovieAndSerialsFragmentBinding

    private val mPopAdapter: CommonAdapter<NetworkItem> = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
            return SerialsViewHolder(parent)
        }
    })

    private val mGenAdapter: CommonAdapter<Genres> = CommonAdapter(object : HolderCreator<Genres> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<Genres> {
            return GenresViewHolder(parent)
        }
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel.responseMovie.observe(this@SerialFragment, this::setMovie)
        mViewModel.responseGenres.observe(this@SerialFragment, this::setGenres)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MovieAndSerialsFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       mBinding.popularity.apply {
           setPageTransformer(ZoomOutPageTransformer())
           adapter = mPopAdapter
           overScrollMode = OVER_SCROLL_NEVER
       }
        mBinding.genres.apply {
            setHasFixedSize(true)
            GridLayoutManager(context, 2).also { this.layoutManager = it }
            isNestedScrollingEnabled = false
            adapter = mGenAdapter
        }
        mBinding.scrollView.apply {
            isSmoothScrollingEnabled = true
            overScrollMode = OVER_SCROLL_NEVER
        }
    }

    private fun setMovie(serial: Serials) {
        mPopAdapter.setTList(serial.result)
    }

    private fun setGenres(genres: List<Genres>) {
        mGenAdapter.setTList(genres)
    }
}