package com.jufaja.inyedashares

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_bbaactivity.*

private const val TAG = "BBAActivity"
class BBAActivity : BAAHomepageActivity() {

    lateinit var tvinlayzbba: TextView
    lateinit var tvcoursezbba: TextView
    lateinit var tvvaluezbba: TextView
    lateinit var tvpartyzbba: TextView
    lateinit var tvmultigrowzbba: TextView
    // old //
    lateinit var tvvaluebbaold: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bbaactivity)

        setUserDate(R.id.tvtodaybba, R.id.tvuserbba)
        setRecyclerBBA()
        oldDataDystrybutor(1, R.id.tvvaluebbaold)
        dataDystrybutor(1, R.id.tvnamezbba, R.id.tvinlayzbba, R.id.tvcoursezbba,
            R.id.tvvaluezbba, R.id.tvpartyzbba, R.id.tvmultigrowzbba,
            R.id.tvmultiperczbba, R.id.tvtotalgrowzbba, R.id.tvtotalperczbba
        )
        swcalactivatebba.setOnCheckedChangeListener { activateButton, isChecked ->
            if (isChecked) {
                etnamezbba.isEnabled = true
                etinlayzbba.isEnabled = true
                etcoursezbba.isEnabled = true
                etpartyzbba.isEnabled = true
                tvvaluebbaold.isGone = true
                tvdummybbaold.isGone = true
                Toast.makeText(this, "Activate Calc. On", Toast.LENGTH_SHORT).show()
            } else {
                etnamezbba.isEnabled = false
                etinlayzbba.isEnabled = false
                etcoursezbba.isEnabled = false
                etpartyzbba.isEnabled = false
                tvvaluebbaold.isGone = false
                tvdummybbaold.isGone = false
                Toast.makeText(this, "Activate Calc. Off", Toast.LENGTH_SHORT).show()
            }
        }
        val activateBtn = findViewById<Button>(R.id.btnrecalbba)
        tvinlayzbba = findViewById(R.id.tvinlayzbba)
        tvcoursezbba = findViewById(R.id.tvcoursezbba)
        tvvaluezbba = findViewById(R.id.tvvaluezbba)
        tvpartyzbba = findViewById(R.id.tvpartyzbba)
        tvvaluebbaold = findViewById(R.id.tvvaluebbaold)
        tvmultigrowzbba = findViewById(R.id.tvmultigrowzbba)
        activateBtn.setOnClickListener {
            changeValue(R.id.etnamezbba, R.id.tvnamezbba)
            changeValue(R.id.etinlayzbba, R.id.tvinlayzbba)
            changeValue(R.id.etcoursezbba, R.id.tvcoursezbba)
            changeValue(R.id.etpartyzbba, R.id.tvpartyzbba)
            caluValueBBA("BBA", tvcoursezbba, tvpartyzbba, tvvaluezbba)
            caluMultiGrow("BBA", tvvaluebbaold, tvvaluezbba, tvmultigrowzbba)
        }
    }
}