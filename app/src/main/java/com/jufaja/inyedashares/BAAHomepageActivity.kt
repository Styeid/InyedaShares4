package com.jufaja.inyedashares

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jufaja.inyedashares.models.DataPost
import com.jufaja.inyedashares.models.User
import kotlinx.android.synthetic.main.activity_baahomepage.*
import kotlinx.android.synthetic.main.activity_bbaactivity.*
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "BAAomepageActivity"
open class BAAHomepageActivity : AppCompatActivity() {

    lateinit var fireStoreDb: FirebaseFirestore
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
        // Showing date in Homescreen
        tvtodayXxX = findViewById(ToDay as Int)
        val currentDay: TextView = this.findViewById(ToDay)
        val dateConventional = SimpleDateFormat("dd.MMM.yyyy")
        val currentDate: String = dateConventional.format(Date())
        currentDay.text = currentDate
        // Showing Username in Homescreen
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