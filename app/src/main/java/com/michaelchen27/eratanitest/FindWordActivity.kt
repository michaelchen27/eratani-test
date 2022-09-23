package com.michaelchen27.eratanitest

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FindWordActivity : AppCompatActivity(), View.OnClickListener {
    private var TAG = FindWordActivity::class.simpleName
    private lateinit var etFindWord: EditText
    private lateinit var tvParagraph: TextView
    private lateinit var tvIndexFound: TextView
    private lateinit var btnSearch: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_word)
        initView()
        initInteractions()
    }

    private fun initView() {
        etFindWord = findViewById(R.id.et_find_word)
        tvParagraph = findViewById(R.id.tv_paragraph)
        tvIndexFound = findViewById(R.id.tv_index_found)
        btnSearch = findViewById(R.id.btn_search)
    }

    private fun initInteractions() {
        btnSearch.setOnClickListener(this)

    }

    // Naive Algo for Pattern Search
    private fun naiveSearch(pat: String, txt: String) {
        val patLen = pat.length
        val txtLen = txt.length

        for (i in 0..txtLen - patLen) {
            var j = 0

            while (j < patLen) {
                if (txt[i + j] != pat[j])
                    break

                j += 1
            }

            if (j == patLen) {
                var text = tvIndexFound.text
                text = "$text $i,"
                tvIndexFound.text = text
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
                if (etFindWord.text.isNotEmpty()) {
                    
                tvIndexFound.text = ""
                val textToFind = etFindWord.text.toString()
                val replaceText = "<span style='background-color:yellow'>$textToFind</span>"
                val originalText = tvParagraph.text.toString()
                val modifiedParagraph =
                    originalText.replace(textToFind, replaceText, ignoreCase = true)
                tvParagraph.text = Html.fromHtml(modifiedParagraph)
                naiveSearch(textToFind, originalText)
                } else {
                    Toast.makeText(this, "Input can not be empty!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}


