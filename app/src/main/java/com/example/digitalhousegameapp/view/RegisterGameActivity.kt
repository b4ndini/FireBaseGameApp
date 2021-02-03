package com.example.digitalhousegameapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.digitalhousegameapp.databinding.ActivityRegisterGameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class RegisterGameActivity : AppCompatActivity() {


    private lateinit var bind: ActivityRegisterGameBinding
    private var image: String? = null
    private val pickImage = 100
    private var imageUri: Uri? = null

    private val fireDB by lazy {
        Firebase.firestore
    }

    private val storageRef by lazy {
        Firebase.storage.reference
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(bind.root)


        bind.civSetImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }

        bind.tilGameNameEdit.addTextChangedListener(registerWatcher)
        bind.tilCreateDateEdit.addTextChangedListener(registerWatcher)
        bind.tilDescriptionEdit.addTextChangedListener(registerWatcher)


        bind.btnSaveGame.setOnClickListener {
            val game = hashMapOf(
                "name" to bind.tilGameNameEdit.text.toString(),
                "date" to bind.tilCreateDateEdit.text.toString(),
                "description" to bind.tilDescriptionEdit.text.toString(),
                "image" to image
            )
            fireDB.collection("games")
                .document()
                .set(game)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@RegisterGameActivity,
                        "Adicionado com sucesso",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@RegisterGameActivity,
                        "Não foi possível adicionar",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            //
            val imgUri: Uri = (imageUri ?: "asd") as Uri
            val gameImage = storageRef.child(
                "${imgUri.lastPathSegment}"  //image and path name to upload
            )
            val uploadTask = gameImage.putFile(imgUri) //upload image to firestore

            uploadTask.addOnFailureListener {
                Toast.makeText(this@RegisterGameActivity, "Erro", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                Log.i("imagem", "carregou com sucesso")


                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    gameImage.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        image = downloadUri.toString()  //gets url image from firestore
                        Log.i("imagem", downloadUri.toString())
                    } else {
                        Toast.makeText(
                            this@RegisterGameActivity,
                            "Erro ao retornar link da imagem",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                //
                bind.civSetImage.setImageURI(imageUri)

            }
        }



    }

    private val registerWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            val nameInput: String = bind.tilGameNameEdit.text.toString().trim()
            val dateInput: String = bind.tilCreateDateEdit.text.toString().trim()
            val descriptionInput: String = bind.tilDescriptionEdit.text.toString().trim()


            enableRegister(nameInput, dateInput, descriptionInput, image)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }




    private fun enableRegister(name: String, date: String, description: String, image: String?) {
        bind.btnSaveGame.isEnabled = !name.isNullOrBlank() && !date.isNullOrBlank() && !description.isNullOrBlank() && !image.isNullOrBlank()
    }


}