package com.example.greenerlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

       verifyUserLoggedIn ()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){

            R.id.create_event_menu ->{

                var createEventIntent = Intent(this, CreateEventActivity::class.java)
                startActivity(createEventIntent)
                // finish the this event
            }
            R.id.sign_out_menu ->{
                FirebaseAuth.getInstance().signOut()
                verifyUserLoggedIn ()
            }

        }


        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private  fun verifyUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null)
        {
            var intent = Intent (this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}
