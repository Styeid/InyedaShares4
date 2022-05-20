package com.jufaja.inyedashares

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jufaja.inyedashares.models.DataPost
import com.jufaja.inyedashares.models.User
import kotlinx.android.synthetic.main.activity_baahomepage.*
import kotlinx.android.synthetic.main.activity_bbaactivity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "BAAHomepageActivity"
open class BAAHomepageActivity : AppCompatActivity() {

    lateinit var fireStoreDb: FirebaseFirestore
    open val datafundsCollectionRef = Firebase.firestore.collection("datafunds")
    lateinit var datapost: MutableList<DataPost>
    lateinit var baaadapter: BAATotalAdapter
    lateinit var bbaadapter: BBAAdapter
    lateinit var tvuserXxX: TextView

    lateinit var tvtodayXxX: TextView

    var signedInUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baahomepage)
        setUserDate(R.id.tvtodaybaa, R.id.tvuserbaa)
        setRecyclerBAA()
    }
    @SuppressLint("SimpleDateFormat")
    open fun setUserDate(ToDay: Any, LoggedUser: Any) {
        // Showing date in Xscreen
        tvtodayXxX = findViewById(ToDay as Int)
        val currentDay: TextView = this.findViewById(ToDay)
        val dateConventional = SimpleDateFormat("dd.MMM.yyyy")
        val currentDate: String = dateConventional.format(Date())
        currentDay.text = currentDate
        // Showing Username in Xscreen
        tvuserXxX = findViewById(LoggedUser as Int)
        fireStoreDb = FirebaseFirestore.getInstance()
        fireStoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                val userNa = signedInUser?.username
                tvuserXxX.text = userNa
                Log.i(TAG, "Signed in user: $signedInUser")
            }
            .addOnFailureListener { exeption ->
                Log.i(TAG, "Failure fetching signed in user", exeption)
            }
        }
    @SuppressLint("NotifyDataSetChanged")
    open fun setRecyclerBAA() {
        // creating RecyclerView for data Firestore
        datapost = mutableListOf()
        baaadapter = BAATotalAdapter(this, datapost)
        rvbaa.adapter = baaadapter
        rvbaa.layoutManager = LinearLayoutManager(this)
        // query to Firestore to retrieve data
        fireStoreDb = FirebaseFirestore.getInstance()
        val datapostReference = fireStoreDb
            .collection("datapost")
            .limit(365)
            .orderBy("abdate", Query.Direction.DESCENDING)
        datapostReference.addSnapshotListener { snapshot, exeption ->
            if (exeption != null || snapshot == null) {
                Log.e(TAG, "exeption querying for datapost('s)")
                return@addSnapshotListener
            }
            val datapostList = snapshot.toObjects(DataPost::class.java)
            datapost.clear()
            datapost.addAll(datapostList)
            baaadapter.notifyDataSetChanged()
            for (datapost in datapostList) {
                Log.i(TAG, "Dokkies $datapost")
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    open fun setRecyclerBBA() {
        // creating RecyclerView for data Firestore
        datapost = mutableListOf()
        bbaadapter = BBAAdapter(this, datapost)
        rvbba.adapter = bbaadapter
        rvbba.layoutManager = LinearLayoutManager(this)
        // query to Firestore to retrieve data
        fireStoreDb = FirebaseFirestore.getInstance()
        val datapostReference = fireStoreDb
            .collection("datapost")
            .limit(365)
            .orderBy("abdate", Query.Direction.DESCENDING)
        datapostReference.addSnapshotListener { snapshot, exeption ->
            if (exeption != null || snapshot == null) {
                Log.e(TAG, "exeption querying for datapost('s)")
                return@addSnapshotListener
            }
            val datapostList = snapshot.toObjects(DataPost::class.java)
            datapost.clear()
            datapost.addAll(datapostList)
            bbaadapter.notifyDataSetChanged()
            for (datapost in datapostList) {
                Log.i(TAG, "Dokkies $datapost")
            }
        }
    }
    open fun oldDataDystrybutor(number: Int, TvA: Any, TvB: Any, TvC: Any, TvD: Any, TvE: Any,
                                TvF: Any, TvG: Any, TvH: Any, TvI: Any
                                ) = CoroutineScope(IO).launch {
        val dataQuer1 = datafundsCollectionRef
            .whereEqualTo("numberz", number)
            .get()
            .await()
        if (dataQuer1.documents.isNotEmpty()) {
            for (data in dataQuer1) {
                val dataBlok1 = datafundsCollectionRef.document(data.id)
                val name = findViewById<TextView>(TvA as Int)
                val inlay = findViewById<TextView>(TvB as Int)
                val course = findViewById<TextView>(TvC as Int)
                val value = findViewById<TextView>(TvD as Int)
                val partys = findViewById<TextView>(TvE as Int)
                val multiGrow = findViewById<TextView>(TvF as Int)
                val multiPerc = findViewById<TextView>(TvG as Int)
                val totalGrow = findViewById<TextView>(TvH as Int)
                val totalPerc = findViewById<TextView>(TvI as Int)
                try {
                    dataBlok1.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                name.hint = document.getString("namez")
                                inlay.hint = document.getString("inlay")/* No z added */
                                course.hint = document.getString("valuez")/* =course */
                                value.hint = document.getString("totalfundz")/* = valuez */
                                partys.hint = document.getString("partysz")
                                multiGrow.hint = document.getString("multigrowz")
                                multiPerc.hint = document.getString("multipercz")
                                totalGrow.hint = document.getString("totalgrowz")
                                totalPerc.hint = document.getString("totalpercz")
                            } else {
                                Toast.makeText(
                                    this@BAAHomepageActivity,
                                    "-1-Error: No Data", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@BAAHomepageActivity,
                                "-2-Error: Fetching failure", Toast.LENGTH_SHORT
                            ).show()
                        }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@BAAHomepageActivity, e.message, Toast.LENGTH_LONG
                        ).show()
                        Log.i(TAG, "-3-Error: Fetching DATA")
                    }
                }
            }
        }
    }
    open fun changeValue(RecalValue: Any, ValueX: Any) {
        val recalValue = findViewById<EditText>(RecalValue as Int)
        val valueX = findViewById<TextView>(ValueX as Int)
        if (recalValue.isEnabled) {
            valueX.setText(recalValue.text)
        } else {
            Toast.makeText(this, "Button is Disabled", Toast.LENGTH_SHORT).show()
        }
    }
    open fun caluValueBBA(Unit: String, CourseBBA0: TextView, PartyBBA1: TextView, FactorizeBBA: TextView)
                            : Map<String, Any> {
        //Calculating_ValueBBA//
        ///Course///
        val dataBBA0 = CourseBBA0.text.toString()
        val dataBBA1 = CourseBBA0.hint.toString()
        ///Partys///
        val dataBBA2 = PartyBBA1.text.toString()
        val dataBBA3 = PartyBBA1.hint.toString()
        val caluMap0 = mutableMapOf<String, Any>()
        if (dataBBA0.isNotEmpty()) {
            caluMap0["Course$Unit"] = dataBBA0
        } else {
            caluMap0["Course$Unit"] = dataBBA1
        }
        if (dataBBA2.isNotEmpty()) {
            caluMap0["Partys$Unit"] = dataBBA2
        } else {
            caluMap0["Partys$Unit"] = dataBBA3
        }
        val keyX0: String? = caluMap0["Course$Unit"] as String?
        val keyY0: String? = caluMap0["Partys$Unit"] as String?
        if (keyX0 != null) {
            if (keyY0 != null) {
                val kapO = (keyX0.toDouble() * keyY0.toDouble())
                val kapFactorizeO = "%.2f".format(kapO)
                ///asTotalFund///
                FactorizeBBA.text = kapFactorizeO
                if (kapFactorizeO.isNotEmpty()) {
                    caluMap0["Value$Unit"] = kapFactorizeO
                } else {
                    Log.i(TAG, "#\n Value= $kapFactorizeO(INVALID)")
                }
            } else {
                Log.i(TAG, "#\n Partys= $dataBBA0(INVALID)")
            }
        } else {
            Log.i(TAG, "#\n Course= $dataBBA2(INVALID)")
        }
        for (entertedInFields in caluMap0) {
            Log.i(TAG, "caluMap0--> $entertedInFields")
        }
        return caluMap0
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bahomepage, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.iclogout){
            Log.i(TAG, "Logging Out!")
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AAAloginActivity::class.java))
            Toast.makeText(this, "Loginscreen", Toast.LENGTH_LONG).show()
        }
        if (item.itemId == R.id.icbaa){
            Log.i(TAG, "Go to BAAscreen")
            val intent = Intent(this, BAAHomepageActivity::class.java)
            finish()
            Toast.makeText(this, "BAA Datascreen", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        if (item.itemId == R.id.icbba){
            Log.i(TAG, "Go to BBAscreen")
            val intent = Intent(this, BBAActivity::class.java)
            finish()
            Toast.makeText(this, "BBA Datascreen", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}