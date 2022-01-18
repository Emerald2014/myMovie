package ru.kudesnik.mymovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kudesnik.mymovie.databinding.MainActivityBinding
import ru.kudesnik.mymovie.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        binding = MainActivityBinding.inflate(getLayoutInflater())
//        val view = binding.root
//        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }
}


/*
setContentView(R.layout.main_fragment)

val recyclerView: RecyclerView = findViewById(R.id.mainFragmentRV)
recyclerView.layoutManager = GridLayoutManager(this, 2)
recyclerView.adapter = MainFragmentAdapter(getMovieCategory())


}

*/