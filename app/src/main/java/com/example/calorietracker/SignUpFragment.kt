package com.example.calorietracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG = "SignUpFragment"
private const val RC_SIGN_UP_GOOGLE = 9002

class SignUpFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        // Inisialisasi GoogleSignInOptions, sesuaikan dengan kebutuhan aplikasi Anda
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val signUpButton: MaterialButton = view.findViewById(R.id.btn_loginGoogle)
        signUpButton.setOnClickListener {
            signUpWithGoogle()
        }

        return view
    }

    private fun signUpWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_UP_GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_UP_GOOGLE) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                firebaseSignUpWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign up failed", e)
            }
        }
    }

    private fun firebaseSignUpWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Pendaftaran berhasil, update UI dengan informasi pengguna yang terdaftar
                    Log.d(TAG, "signUpWithCredential:success")
                    val user = mAuth.currentUser
                    // updateUI(user);

                    // Redirect atau lakukan tindakan lain setelah pendaftaran berhasil
                } else {
                    // Jika pendaftaran gagal, tampilkan pesan kepada pengguna.
                    Log.w(TAG, "signUpWithCredential:failure", task.exception)
                    // updateUI(null);
                }
            }
    }
}
