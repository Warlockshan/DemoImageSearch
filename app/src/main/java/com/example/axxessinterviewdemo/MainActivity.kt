package com.example.axxessinterviewdemo

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.axxessinterviewdemo.adapter.WordListAdapter
import com.example.axxessinterviewdemo.roomDatabase.entity.Word
import com.example.axxessinterviewdemo.roomDatabase.viewModel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var wordViewModel: WordViewModel
    lateinit var imageIDs: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rec_comments)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "New Activity"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)




        editWordView = findViewById(R.id.et_comment)
        val button = findViewById<ImageView>(R.id.iv_add_comments)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        /*wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })*/
        if (intent.extras!=null){
            val imageName=intent.getStringExtra("imageName")
            val imageId = intent.getStringExtra("imageId")
            val imageUrl = intent.getStringExtra("imageUrl")
            val specificImage = findViewById<ImageView>(R.id.iv_specific_image)
            val titleText = findViewById<TextView>(R.id.imageTitle);
            titleText.text = imageName
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(specificImage)
            wordViewModel.getAllByListCategoryId(imageId).observe(this, Observer { words ->
                words?.let {
                    if (it.isNotEmpty()){
                        adapter.setWords(it)
                        val textNoComments = findViewById<TextView>(R.id.tv_no_comments)
                        textNoComments.visibility = View.GONE
                    }else{
                        val textNoComments = findViewById<TextView>(R.id.tv_no_comments)
                        textNoComments.visibility = View.VISIBLE
                    }

                }
            })
            imageIDs=imageId
        }




        button.setOnClickListener {
            if (editWordView.length()>0){
                val word = Word(0,imageIDs,editWordView.text.toString())
                wordViewModel.insert(word)
                et_comment.text.clear()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(et_comment.windowToken, 0)
            }else{
                editWordView.setError("Please write comments!")
            }


        }



    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}