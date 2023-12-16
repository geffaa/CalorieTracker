package com.example.calorietracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.example.calorietracker.GetStarted.GS1_InputNameActivity
import com.example.calorietracker.HomePage.HomePageActivity
import com.example.calorietracker.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore

private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var Auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val PREFS_NAME = "MyPrefsFile"
    private val firestore = Firebase.firestore

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                Auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(requireActivity(), GS1_InputNameActivity::class.java))
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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

        binding.btnGetStarted.setOnClickListener {
            val email = binding.textInputLayoutEmail.editText?.text.toString().trim()
            val password = binding.textInputLayoutPassword.editText?.text.toString().trim()
            val confirmPassword =
                binding.textInputLayoutConfirmationPassword.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Email, Password, atau Confirm Password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password != confirmPassword) {
                Toast.makeText(
                    requireContext(),
                    "Password dan Confirm Password tidak cocok",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = Auth.currentUser
                            user?.let {
                                // Simpan data pengguna ke Firestore
                                val userData = hashMapOf(
                                    "email" to email,
                                    "role" to "user"
                                )

                                firestore.collection("users").document(it.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "DocumentSnapshot successfully written!")
                                        saveLoginInfo(email)
                                        startActivity(
                                            Intent(
                                                requireActivity(),
                                                HomePageActivity::class.java
                                            )
                                        )
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error writing document", e)
                                    }
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), "Sign Up gagal.", Toast.LENGTH_SHORT)
                                .show()
                        }
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

    override fun onStart() {
        super.onStart()

        val sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("user_email", null)

        if (FirebaseAuth.getInstance().currentUser != null || userEmail != null) {
            startActivity(Intent(requireActivity(), HomePageActivity::class.java))
        }
    }
}