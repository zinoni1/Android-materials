package com.zenonrodrigo.cotitzacions_materials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var textpreu: TextView
    private lateinit var textgrams: TextView
    private lateinit var textresultat: TextView
    private lateinit var buttonDeleteAll: Button
    private lateinit var buttonDeleteOne: Button
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("calculText", textpreu.text.toString())
        outState.putString("resultText", textresultat.text.toString())
        outState.putString("gramsText", textgrams.text.toString())
        outState.putString("selectedMaterial", selectedMaterial)
        outState.putDouble("oroValor", oroValor ?: Double.NaN)
        outState.putDouble("cobreValor", cobreValor ?: Double.NaN)
        outState.putDouble("plataValor", plataValor ?: Double.NaN)
        outState.putDouble("platinoValor", platinoValor ?: Double.NaN)
        outState.putDouble("userNumber", userNumber ?: Double.NaN)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        textpreu.text = savedInstanceState.getString("calculText")
        textresultat.text = savedInstanceState.getString("resultText")
        textgrams.text = savedInstanceState.getString("gramsText")
        selectedMaterial = savedInstanceState.getString("selectedMaterial", "")

        oroValor = savedInstanceState.getDouble("oroValor").let { if (it.isNaN()) null else it }
        cobreValor = savedInstanceState.getDouble("cobreValor").let { if (it.isNaN()) null else it }
        plataValor = savedInstanceState.getDouble("plataValor").let { if (it.isNaN()) null else it }
        platinoValor = savedInstanceState.getDouble("platinoValor").let { if (it.isNaN()) null else it }
        userNumber = savedInstanceState.getDouble("userNumber").let { if (it.isNaN()) null else it }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textpreu = findViewById(R.id.preu)
        textresultat = findViewById(R.id.resultat)
        textgrams = findViewById(R.id.grams)
        textpreu.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateResultat()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No hacemos nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tampoco aquí
            }
        })
        if (savedInstanceState != null) {
            textpreu.text = savedInstanceState.getString("calculText")
            textresultat.text = savedInstanceState.getString("resultText")
            textpreu.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    updateResultat()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No necesitamos hacer nada aquí
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Tampoco aquí
                }
            })

        }
        botoComa()
        val botoOr = findViewById<Button>(R.id.buttonOr)
        val botoPlata = findViewById<Button>(R.id.buttonPlata)
        val botoCoure = findViewById<Button>(R.id.buttonCoure)
        val botoPlatino = findViewById<Button>(R.id.buttonPlatino)
        setupButton(botoOr, "Oro")
        setupButton(botoCoure, "Cobre")
        setupButton(botoPlata, "Plata")
        setupButton(botoPlatino, "Platino")
        botoOr.setOnClickListener {
            selectedMaterial = getString(R.string.oro)
            if (oroValor == null) {
                botonsmaterials { value -> oroValor = value }
            } else {
                userNumber = oroValor
                oroValor?.let { updateTextGrams(it) }
                updateResultat()
            }
        }

        botoCoure.setOnClickListener {
            selectedMaterial = getString(R.string.cobre)
            if (cobreValor == null) {
                botonsmaterials { value -> cobreValor = value }
            } else {
                userNumber = cobreValor
                cobreValor?.let { updateTextGrams(it) }
                updateResultat()
            }
        }

        botoPlata.setOnClickListener {
            selectedMaterial = getString(R.string.plata)
            if (plataValor == null) {
                botonsmaterials { value -> plataValor = value }
            } else {
                userNumber = plataValor
                plataValor?.let { updateTextGrams(it) }
                updateResultat()
            }
        }

        botoPlatino.setOnClickListener {
            selectedMaterial = getString(R.string.platino)
            if (platinoValor == null) {
                botonsmaterials { value -> platinoValor = value }
            } else {
                userNumber = platinoValor
                platinoValor?.let { updateTextGrams(it) }
                updateResultat()
            }
        }

        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button01)
        val button2 = findViewById<Button>(R.id.button02)
        val button3 = findViewById<Button>(R.id.button03)
        val button4 = findViewById<Button>(R.id.button04)
        val button5 = findViewById<Button>(R.id.button05)
        val button6 = findViewById<Button>(R.id.button06)
        val button7 = findViewById<Button>(R.id.button07)
        val button8 = findViewById<Button>(R.id.button08)
        val button9 = findViewById<Button>(R.id.button09)
        val textpreu = findViewById<TextView>(R.id.preu)

        fun addtext(texto: String) {
            val textActual = textpreu.text.toString()

            if (texto.toIntOrNull() != null && textActual.contains(",")) {
                val parts = textActual.split(",")
                if (parts[1].length >= 6) return
            }

            if (textActual == "0") {
                textpreu.text = texto
            } else {
                textpreu.text = textActual + texto
            }
        }
        button0.setOnClickListener {
            addtext("0")
        }
        button1.setOnClickListener {
            addtext("1")
        }
        button2.setOnClickListener {
            addtext("2")
        }
        button3.setOnClickListener {
            addtext("3")
        }
        button4.setOnClickListener {
            addtext("4")
        }
        button5.setOnClickListener {
            addtext("5")
        }
        button6.setOnClickListener {
            addtext("6")
        }
        button7.setOnClickListener {
            addtext("7")
        }
        button8.setOnClickListener {
            addtext("8")
        }
        button9.setOnClickListener {
            addtext("9")
        }
        textpreu.text = "0"
        buttonDeleteAll = findViewById<Button>(R.id.buttonCE)
        buttonDeleteOne = findViewById<Button>(R.id.buttonEsborrar)

        buttonDeleteAll.setOnClickListener {
            textpreu.text = "0"
        }
        buttonDeleteOne.setOnClickListener {
            val text = textpreu.text.toString()
            if (text.isNotEmpty()) {
                textpreu.text = text.substring(0, text.length - 1)
            }

            if (textpreu.text.isEmpty()) {
                textpreu.text = "0"
            }
        }
    }

    fun botoComa() {
        val textpreu = findViewById<TextView>(R.id.preu)
        val botoComa = findViewById<Button>(R.id.buttonComa)

        fun addtext(texto: String) {
            val textActual = textpreu.text.toString()
            textpreu.text = textActual + texto
        }

        botoComa.setOnClickListener {
            val preuText = textpreu.text.toString()
            if (!preuText.contains(",")) {
                addtext(",")
            }
        }
    }
    var oroValor: Double? = null
    var cobreValor: Double? = null
    var plataValor: Double? = null
    var platinoValor: Double? = null
    var userNumber: Double? = null
    var selectedMaterial: String = ""

    fun setupButton(button: Button, material: String) {
        button.setOnClickListener {
            selectedMaterial = material
            when (material) {
                "Oro" -> {
                    if (oroValor == null) {
                        botonsmaterials { value -> oroValor = value }
                    } else {
                        userNumber = oroValor
                        updateResultat()
                    }
                }
                "Cobre" -> {
                    if (cobreValor == null) {
                        botonsmaterials { value -> cobreValor = value }
                    } else {
                        userNumber = cobreValor
                        updateResultat()
                    }
                }
                "Plata" -> {
                    if (plataValor == null) {
                        botonsmaterials { value -> plataValor = value }
                    } else {
                        userNumber = plataValor
                        updateResultat()
                    }
                }
                "Platino" -> {
                    if (platinoValor == null) {
                        botonsmaterials { value -> platinoValor = value }
                    } else {
                        userNumber = platinoValor
                        updateResultat()
                    }
                }
            }
        }
    }


    fun botonsmaterials(onValueSet: (Double) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Introduce un valor para $selectedMaterial")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            val userInput = input.text.toString()
            val inputValue = userInput.toDoubleOrNull()
            if (inputValue != null) {
                onValueSet(inputValue)
                userNumber = inputValue
                updateTextGrams(inputValue)
                updateResultat()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    fun updateTextGrams(value: Double) {
        textgrams.text = "$value ${getString(R.string.precio_por_gramo)} $selectedMaterial"
    }


    fun updateResultat() {
        userNumber?.let {
            val preuValue = textpreu.text.toString().replace(',', '.').toDoubleOrNull() ?: 0.0

            val resultValue = it * preuValue
            textresultat.text = resultValue.toString().replace('.', ',')
        }
    }
}




