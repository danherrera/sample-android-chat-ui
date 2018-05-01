package io.agileninja.androidchatui.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import io.agileninja.androidchatui.R

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer = findViewById(R.id.fragment_container)

        savedInstanceState ?: run {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, ChatFragment.newInstance(), "chat")
                    .addToBackStack("chat")
                    .commit()
        }
    }

}
