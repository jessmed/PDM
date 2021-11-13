package com.medyesus.myapplication.ui.books

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.colorpicker.util.setVisibility
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.medyesus.myapplication.R
import com.medyesus.myapplication.UserStartInfo.Companion.prefs
import com.medyesus.myapplication.databinding.FragmentBooksBinding


class YourBooksFragment : Fragment() {

    private lateinit var yourBooksViewModel: YourBooksViewModel
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        yourBooksViewModel =
                ViewModelProvider(this).get(YourBooksViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentBooksBinding>(inflater,
            R.layout.fragment_books,container,false)
        var text = ""

        if(prefs.getSession()){

            // Accedemos a la base de datos
            var docRef = db.collection("users")
                .document(prefs.getEmail())
                .collection("reservas")
                .document("reserva1")

            docRef.get().addOnSuccessListener {
                text += (it.get("casa") as String?).toString() + "\n"
                text += (it.get("entrada") as String?).toString() + "\n"
                text += (it.get("salida") as String?).toString() + "\n"
                text += (it.get("dias") as String?).toString() + "d.\n"
                text += (it.get("precio") as String?).toString() + "€\n"

                //Si accedemos a la base de datos correctamente significa que existe
                //y hacemos visible el botón
                binding.book1.setVisibility(visible = true)
            }
        }

        binding.book1.setOnClickListener(){
            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle(R.string.bookdialogtitle)
                .setMessage(text)
                .setPositiveButton(R.string.dialog_one_style_negative_btn) { view, _ ->
                    view.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()
        }

        return binding.root
    }
}