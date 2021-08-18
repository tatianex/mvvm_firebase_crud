package com.proway.mvvm_solo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.proway.mvvm_solo.utils.replaceView
import com.proway.mvvm_solo.view.ContentFragment
import com.proway.mvvm_solo.view.SignInFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (FirebaseAuth.getInstance().currentUser != null) {
            replaceView(ContentFragment.newInstance())
        } else {
            replaceView(SignInFragment.newInstance())
        }
    }
}