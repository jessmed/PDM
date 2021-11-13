package com.medyesus.myapplication.ui.books

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.medyesus.myapplication.R
import com.medyesus.myapplication.UserStartInfo.Companion.prefs
import com.medyesus.myapplication.databinding.FragmentBookPageBinding
import java.util.*


class BookPageFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val myReserva = Reserva()
    private var fechaentrada:Int = 0
    private var fechasalida:Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentBookPageBinding>(inflater,
            R.layout.fragment_book_page,container,false)

        // Configuramos el spinner para elegir casa
        val lista = resources.getStringArray(R.array.opciones)
        val adaptador = ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,lista)
        binding.spinner.adapter = adaptador

        // Configuramos el calendario para elegir fechas
        binding.inDate.setOnClickListener{ showDatePicker()}
        binding.outDate.setOnClickListener{ showDatePicker2()}


        binding.btnprice.setOnClickListener(){

            // Si las dos fechas se han escogido se calculan los dias si no mensaje alerta

            // Obtenemos mensajes del edittext
            val msg1: String = binding.inDate.text.toString()
            val msg2: String = binding.outDate.text.toString()

            // Si no estan vacíos procedemos a calcular el precio
            if(msg1.trim().isNotEmpty() && msg2.trim().isNotEmpty()){
                // Calculamos el número de días seleccionados restando y calculamos el precio
                val days = fechasalida - fechaentrada
                val precioFinal = days * 20

                // Actualizamos valores instancia reserva

                myReserva.entrada = msg1
                myReserva.salida = msg2
                myReserva.dias = days.toString()
                myReserva.precio = precioFinal.toString()
                myReserva.casa = binding.spinner.selectedItem.toString()

                // Según el lenguaje que tengamos se imprimira un mensaje
                binding.price.setText(getString(R.string.entrada) + msg1 + "\n"+
                        getString(R.string.salida) + msg2 + "\n"+
                        getString(R.string.dias) + myReserva.dias + "\n"+
                        getString(R.string.preciodia) + "20€ \n"+
                        "-----------------------------------------\n"+
                        getString(R.string.precio) + myReserva.precio+" €")


            }else{
                Toast.makeText(requireActivity(), requireActivity().getString(R.string.toastDays) , Toast.LENGTH_SHORT).show()
            }
        }

        // Configuramos la condición de que si no ha inicado sesión se dispare un mensaje de alerta
        binding.finalizaReserva.setOnClickListener(){
            if(prefs.getSession()){
                //Cambiar
                if(binding.price.text.isEmpty()){
                    Toast.makeText(requireActivity(), requireActivity().getString(R.string.toastEnd) , Toast.LENGTH_SHORT).show()
                }else {
                    db.collection("users").document(prefs.getEmail())
                        .collection("reservas")
                        .document("reserva1").set(
                            hashMapOf(
                            "entrada" to myReserva.entrada,
                            "salida" to myReserva.salida,
                            "dias" to myReserva.dias,
                            "precio" to myReserva.precio,
                            "casa" to myReserva.casa)
                    )

                    view?.findNavController()?.navigate(R.id.action_bookPageFragment_to_nav_home)
                }
            }
            else{
                val dialog = AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.dialog_one_style_title)
                    .setMessage(R.string.dialog_one_style_message)
                    .setPositiveButton(R.string.dialog_one_style_negative_btn) { view, _ ->
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

                dialog.show()
            }
        }

        return binding.root
    }


    private fun showDatePicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }

        getFragmentManager()?.let { datePicker.show(it,"dataPicker") }
    }

    private fun showDatePicker2() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected2(day, month, year) }

        getFragmentManager()?.let { datePicker.show(it,"dataPicker") }
    }

    fun onDateSelected(day:Int,month:Int,year:Int){
        val iDate = requireView().findViewById<View>(R.id.inDate) as TextView
        iDate.setText("$day-$month-$year")
        fechaentrada = day
    }
    fun onDateSelected2(day:Int,month:Int,year:Int){
        val oDate = requireView().findViewById<View>(R.id.outDate) as TextView
        oDate.setText("$day-$month-$year")
        fechasalida = day
    }

}