package com.jufaja.inyedashares

import android.os.Bundle

class BBAActivity : BAAHomepageActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbaactivity)
        setUserDate(R.id.tvtodaybba, R.id.tvuserbba)
        setRecyclerBBA()
    }
}