package com.example.calorietracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.calorietracker.GetStarted.GS1_InputNameActivity
import com.example.calorietracker.GetStarted.GetStartedActivity
import com.example.calorietracker.HomePage.HomePageActivity
import com.example.calorietracker.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var Auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val PREFS_NAME = "MyPrefsFile"
    private val firestore = FirebaseFirestore.getInstance()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                Auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(requireActivity(), GetStartedActivity::class.java))
                    } else {
                        Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        Auth = FirebaseAuth.getInstance()

        binding.btnLoginGoogle.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
            val password = binding.textInputLayoutPassword.editText?.text.toString().trim()

            Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = Auth.currentUser
                        user?.let {
                            // Pengecekan role dari Firestore
                            firestore.collection("users").document(it.uid)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document != null && document.exists()) {
                                        val role = document.getString("role")

                                        if (role == "admin") {
                                            startActivity(
                                                Intent(
                                                    requireActivity(),
                                                    HomePageActivity::class.java
                                                )
                                            )
                                        } else {
                                            // Pengecekan apakah data GetStarted sudah diisi atau belum
                                            val sharedPref = requireActivity().getSharedPreferences(
                                                PREFS_NAME,
                                                Context.MODE_PRIVATE
                                            )
                                            val isGetStartedCompleted = sharedPref.getBoolean(
                                                "is_get_started_completed",
                                                false
                                            )

                                            if (isGetStartedCompleted) {
                                                startActivity(
                                                    Intent(
                                                        requireActivity(),
                                                        HomePageActivity::class.java
                                                    )
                                                )
                                            } else {
                                                startActivity(
                                                    Intent(
                                                        requireActivity(),
                                                        GetStartedActivity::class.java
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(TAG, "get failed with ", exception)
                                }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Autentikasi gagal. Periksa email dan password Anda.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun saveLoginInfo(email: String) {
        val sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user_email", email)
        editor.apply()
    }

    private fun checkIfFirstTimeLogin(email: String) {
        Auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods
                if (signInMethods?.isEmpty() == true) {
                    // User baru, arahkan ke GetStartedActivity
                    startActivity(Intent(requireActivity(), GetStartedActivity::class.java))
                } else {
                    // User sudah pernah login, arahkan ke HomeActivity
                    startActivity(Intent(requireActivity(), HomePageActivity::class.java))
                }
            } else {
                // Jika check gagal, tampilkan pesan ke pengguna.
                Toast.makeText(requireContext(), "Failed to check user.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onStart() {
        super.onStart()

        val sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null)

        if (FirebaseAuth.getInstance().currentUser != null || userEmail != null) {
            startActivity(Intent(requireActivity(), HomePageActivity::class.java))
        }
    }
}