package com.michaelchen27.eratanitest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BottomSheetFragment(filterListener: OnFilterListener) : BottomSheetDialogFragment(),
    View.OnClickListener {

    private val TAG = BottomSheetFragment::class.simpleName
    private lateinit var cgFilterCategory: ChipGroup
    private lateinit var cgSortDownload: ChipGroup
    private lateinit var btnFilter: Button

    private var onFilterListener: OnFilterListener

    init {
        this.onFilterListener = filterListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initInteractions()
    }

    private fun initView(view: View) {
        cgFilterCategory = view.findViewById(R.id.cg_filter_category)
        cgSortDownload = view.findViewById(R.id.cg_sort_download)
        btnFilter = view.findViewById(R.id.btn_filter)
    }

    private fun initInteractions() {
        setupFilterCategoryChip()
        cgFilterCategory.isSingleSelection = true
        cgSortDownload.isSingleSelection = true

        btnFilter.setOnClickListener(this)
    }


    private fun setupFilterCategoryChip() {
        val movieCategories: List<String> = listOf("Drama", "Romance", "Action", "Comedy", "Horror")
        for (i in movieCategories.indices) {
            val chip: Chip =
                layoutInflater.inflate(R.layout.filter_chip, cgFilterCategory, false) as Chip
            chip.id = i
            chip.tag = "chip_location_$i"
            chip.text = movieCategories[i]
            cgFilterCategory.addView(chip)
        }
    }

    private fun getSelectedCategoryChip(): String {
        var categoryName = ""
        val ids: List<Int> = cgFilterCategory.checkedChipIds
        if (ids.isNotEmpty()) {
            for (id in ids) {
                val chip: Chip = cgFilterCategory.findViewById(id)
                categoryName = chip.text.toString()
            }
            return categoryName
        } else {
            return ""
        }
    }

    private fun getSelectedSortTypeChip(): Int {
        var chosenSortType = 0
        val ids: List<Int> = cgSortDownload.checkedChipIds
        if (ids.isNotEmpty()) {
            for (id in ids) {
                chosenSortType = id
            }
            return chosenSortType
        } else {
            return 0
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_filter -> {
                val categoryName = getSelectedCategoryChip()
                val sortType = getSelectedSortTypeChip()
                onFilterListener.onFilter(categoryName, sortType)
                this.dismiss()
            }
        }
    }

    interface OnFilterListener {
        fun onFilter(categoryName: String, sortType: Int)
    }

}