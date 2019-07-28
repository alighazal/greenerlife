package com.example.greenerlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    val TAG= "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
        login()



    }

    private fun login (){
        tv_login_register.setOnClickListener {

            var intent =  Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setup(){

        auth = FirebaseAuth.getInstance()

        var username: String? = null
        var email: String? = null
        var password: String? = null
        var uid: String

        btn_signup_register.setOnClickListener {

            username= et_name_register.text.toString()
            email = et_email_register.text.toString()
            password = et_password_register.text.toString()
            uid = auth.uid!!

            if (email != null && password != null && username != null)
            {
                auth.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            saveUserToFirebase(uid, email!!, username!!)

                            var intent = Intent(this, EventsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }


        }
    }

    private fun saveUserToFirebase (uid: String, email:String, username:String){

        var user = User (uid,email , username )

        var ref = db.collection("users")
            .add(user).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "UserSaved: success")

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "UserSaved: failure", task.exception)
            }
        }

    }

    class User( val uid: String, val email: String,val username:String)

}
