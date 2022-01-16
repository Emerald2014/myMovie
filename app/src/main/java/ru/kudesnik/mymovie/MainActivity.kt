package ru.kudesnik.mymovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.model.entities.getMovieCategory
import ru.kudesnik.mymovie.model.entities.getMovieCategoryString
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.adapters.TestAdapter
import ru.kudesnik.mymovie.ui.list.ListFragment
import ru.kudesnik.mymovie.ui.main.MainFragment
import ru.kudesnik.mymovie.ui.main.TestFragmentRV

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment)

        val recyclerView: RecyclerView = findViewById(R.id.mainFragmentRV)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = TestAdapter(getMovieCategory())
    }

    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach { i -> data.add("$i element") }
        return data
    }
}



        /*
setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TestFragmentRV())
                .commitNow()
        }
    }
}

 */