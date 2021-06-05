package com.example.kino.screen.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.kino.R
import com.example.kino.databinding.GenresChipGroupBinding
import com.example.kino.network.model.common.GenresApi
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class GenresGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onClickListener: (id: GenresApi) -> Unit = {}
    private var _binding: GenresChipGroupBinding? = null
    private val binding get() = _binding!!

    fun setOnClickListener(listener: (id: GenresApi) -> Unit) {
        onClickListener = listener
    }

    init {
        val inflater = LayoutInflater.from(context)
        _binding = GenresChipGroupBinding.inflate(inflater, this, false)
        addView(binding.root)
    }

    fun setGenres(genres: List<GenresApi>) {
        val rootWidth = context.resources.displayMetrics.widthPixels
        genres.forEachIndexed { index, genre ->
            val chip = createChip(index, genre)

            val chipWidth = chip.measureAndGetWidth()
            val firstRowWidth = binding.firstRow.measureAndGetWidth()
            val secondRowWidth = binding.secondRow.measureAndGetWidth()
            val threeRowWidth = binding.threeRow.measureAndGetWidth()

            when {
                rootWidth > firstRowWidth + chipWidth -> binding.firstRow.addChip(chip)
                rootWidth > secondRowWidth + chipWidth -> binding.secondRow.addChip(chip)
                rootWidth > threeRowWidth + chipWidth -> binding.threeRow.addChip(chip)
                else -> binding.threeRow.addChip(chip)
            }
        }
    }

    private fun createChip(index: Int, genresApi: GenresApi) = chip().apply {
        id = index
        text = genresApi.name
        setOnClickListener {
            onClickListener(genresApi)
        }
    }

    private fun chip() = LayoutInflater.from(context)
        .inflate(R.layout.genre_chip, binding.root, false) as Chip

    private fun ChipGroup.addChip(chip: Chip) {
        isVisible = true
        this.addView(chip)
    }

    fun onDestroy() {
        binding.root.removeAllViews()
        _binding = null
    }
}

private fun View.measureAndGetWidth(): Int {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    return measuredWidth
}