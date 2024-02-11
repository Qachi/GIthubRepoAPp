package com.example.ktrecyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.ktrecyclerview.adapter.RecyclerAdapter
import com.example.ktrecyclerview.databinding.ActivityMainBinding
import com.example.ktrecyclerview.model.Item
import com.example.ktrecyclerview.viewmodel.GitHubRepoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewModel: GitHubRepoViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProvider(this, GitHubRepoViewModel.Factory(activity.application))
            .get(GitHubRepoViewModel::class.java)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view =binding.root
            setContentView(view)

        initRecyclerView()

        startFetchGithubRepo()
        viewModel.gitHubItemLiveData.observe(this) {
            recyclerAdapter.submitList(it)
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progress.visibility = View.VISIBLE
            } else binding.progress.visibility = View.GONE
        }
    }

    private fun startFetchGithubRepo() {
        viewModel.startFetchGithubRepo()
    }

    fun startDetailScreen(item: Item?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_DETAILS", item)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            recyclerAdapter =
                RecyclerAdapter(object : RecyclerAdapter.RecyclerItemClickListener {
                    override fun onItemClick(itemView: Item, position: Int) {
                        startDetailScreen(itemView)
                    }
                })
            adapter = recyclerAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.menu.refresh_menu) {
            startFetchGithubRepo()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
