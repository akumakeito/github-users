package ru.akumakeito.githubusers.presentation

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.githubusers.R
import ru.akumakeito.githubusers.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btn_user).setOnClickListener {
           viewModel.getUserByUsername("akumakeito")
        }

        findViewById<Button>(R.id.btn_user_list).setOnClickListener {
            viewModel.getUserList()
        }

    }

}