package com.zenonrodrigo.cotitzacions_materials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
class MainActivity : AppCompatActivity() {
    private lateinit var textpreu: TextView
    private lateinit var textgrams: TextView
    private lateinit var textresultat: TextView
    private lateinit var botoCE: Button
    private lateinit var botoEsborrar: Button
    //aquesta funció guarda els valors del activity
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("calculText", textpreu.text.toString())
        outState.putString("resultText", textresultat.text.toString())
        outState.putString("gramsText", textgrams.text.toString())
        outState.putString("MaterialSelec", MaterialSeleccionat)
        outState.putDouble("oroValor", oroValor ?: Double.NaN)
        outState.putDouble("cobreValor", cobreValor ?: Double.NaN)
        outState.putDouble("plataValor", plataValor ?: Double.NaN)
        outState.putDouble("platinoValor", platinoValor ?: Double.NaN)
        outState.putDouble("numeroIntroduit", numeroIntroduit ?: Double.NaN)
    }
    //aquesta aplica el activity al altre per poder seguir el que estava fent
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textpreu.text = savedInstanceState.getString("calculText")
        textresultat.text = savedInstanceState.getString("resultText")
        textgrams.text = savedInstanceState.getString("gramsText")
        MaterialSeleccionat = savedInstanceState.getString("MaterialSelec", "")
        oroValor = savedInstanceState.getDouble("oroValor").let { if (it.isNaN()) null else it }
        cobreValor = savedInstanceState.getDouble("cobreValor").let { if (it.isNaN()) null else it }
        plataValor = savedInstanceState.getDouble("plataValor").let { if (it.isNaN()) null else it }
        platinoValor = savedInstanceState.getDouble("platinoValor").let { if (it.isNaN()) null else it }
        numeroIntroduit = savedInstanceState.getDouble("numeroIntroduit").let { if (it.isNaN()) null else it }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textpreu = findViewById(R.id.preu)
        textresultat = findViewById(R.id.resultat)
        textgrams = findViewById(R.id.grams)
//si s'ha guardat fa l'if
        if (savedInstanceState != null) {
            //actualitzem el text del preu
            textpreu.text = savedInstanceState.getString("calculText")
            //actualtzem el text del resultat
            textresultat.text = savedInstanceState.getString("resultText")
            //un textwatcher per actualitzar el resultat
            textpreu.addTextChangedListener(object : TextWatcher {
                //un cop ha canviat el text actualitza el resultat
                override fun afterTextChanged(s: Editable?) {
                    actualitzarResultat()
                }
                //tot aixo no fa res, pero es del textwatcher
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
        botoComa()

        val botoOr = findViewById<Button>(R.id.buttonOr)
        val botoPlata = findViewById<Button>(R.id.buttonPlata)
        val botoCoure = findViewById<Button>(R.id.buttonCoure)
        val botoPlatino = findViewById<Button>(R.id.buttonPlatino)
        botonsMaterials(botoOr, "Oro")
        botonsMaterials(botoCoure, "Cobre")
        botonsMaterials(botoPlata, "Plata")
        botonsMaterials(botoPlatino, "Platino")
        //quan cliques un dels materials
        botoOr.setOnClickListener {
            MaterialSeleccionat = getString(R.string.oro)
            //mira si or te un valor o no
            if (oroValor == null) {
                //si no te mostra el dialog
                botonsmaterials { valor -> oroValor = valor }
            } else {
                //agafa el valor de or
                numeroIntroduit = oroValor
                //actualitza el text dels grams
                oroValor?.let { actualitzarTextGrams(it) }
                //i tambe actualitza el resultat
                actualitzarResultat()
            }
        }
        //el mateix que or pero amb coure
        botoCoure.setOnClickListener {
            MaterialSeleccionat = getString(R.string.cobre)
            if (cobreValor == null) {
                botonsmaterials { valor -> cobreValor = valor }
            } else {
                numeroIntroduit = cobreValor
                //actualitza el text dels grams
                cobreValor?.let { actualitzarTextGrams(it) }
                //i tambe actualitza el resultat
                actualitzarResultat()
            }
        }
        //el mateix que or pero amb plata
        botoPlata.setOnClickListener {
            MaterialSeleccionat = getString(R.string.plata)
            if (plataValor == null) {
                botonsmaterials { valor -> plataValor = valor }
            } else {
                numeroIntroduit = plataValor
                plataValor?.let { actualitzarTextGrams(it) }
                actualitzarResultat()
            }
        }
        //el mateix que or pero amb platino
        botoPlatino.setOnClickListener {
            MaterialSeleccionat = getString(R.string.platino)
            if (platinoValor == null) {
                botonsmaterials { valor -> platinoValor = valor }
            } else {
                numeroIntroduit = platinoValor
                platinoValor?.let { actualitzarTextGrams(it) }
                actualitzarResultat()
            }
        }
        //declaro els botons
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

        //funcio per posar el text
        fun addtext(text: String) {
            //el text que esta ara mateix posat
            val textActual = textpreu.text.toString()
            //si el text actual te una coma i mira si es pot convertir a Int
            if (text.toIntOrNull() != null && textActual.contains(",")) {
                //divideix on esta la coma
                val decimal = textActual.split(",")
                //si despres de la coma ja te 6 decimals surt de la funcio
                if (decimal[1].length >= 6) return
            }
            //si el text es 0 canvia el text
            if (textActual == "0") {
                textpreu.text = text
            } else {
                //si no es 0 posa el text que hi havia mes el nou
                textpreu.text = textActual + text
            }
        }
        //quan es cliquen els numeros fa la funcio de addtext (la anterior)
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
        botoCE = findViewById<Button>(R.id.buttonCE)
        botoEsborrar = findViewById<Button>(R.id.buttonEsborrar)
        //quan li dona al boto de "borrar tot" es posa un 0
        botoCE.setOnClickListener {
            textpreu.text = "0"
        }
        //quan li dona al boto de borrar 1
        botoEsborrar.setOnClickListener {
            val text = textpreu.text.toString()
            //si el text no esta buit
            if (text.isNotEmpty()) {
                //elimina l'ultim numero
                textpreu.text = text.substring(0, text.length - 1)
            }
            //si al eliminar el numero el text queda buit es posa un 0
            if (textpreu.text.isEmpty()) {
                textpreu.text = "0"
            }
        }
    }

    fun botoComa() {
        val textpreu = findViewById<TextView>(R.id.preu)
        val botoComa = findViewById<Button>(R.id.buttonComa)
        //faig un altre cop la funcio per afegir la coma
        fun addtext(texto: String) {
            val textActual = textpreu.text.toString()
            textpreu.text = textActual + texto
        }
        //al clicar el boto de la coma
        botoComa.setOnClickListener {
            val preuText = textpreu.text.toString()
            //si no conte una coma es posa
            if (!preuText.contains(",")) {
                addtext(",")
            }
        }
    }
    var oroValor: Double? = null
    var cobreValor: Double? = null
    var plataValor: Double? = null
    var platinoValor: Double? = null
    var numeroIntroduit: Double? = null
    var MaterialSeleccionat: String = ""
    fun botonsMaterials(boto: Button, material: String) {
        //quan clica un material
        boto.setOnClickListener {
            //switch amb un string dels materials
            when (MaterialSeleccionat) {
                "Oro" -> {
                    //mira si or te un valor o no
                    if (oroValor == null) {
                        //si no te mostra el dialog
                        botonsmaterials { valor -> oroValor = valor }
                    } else {
                        //agafa el valor del or
                        numeroIntroduit = oroValor
                        //actualitza el resultat
                        actualitzarResultat()
                    }
                }
                //el mateix amb coure
                "Cobre" -> {
                    if (cobreValor == null) {
                        botonsmaterials { valor -> cobreValor = valor }
                    } else {
                        numeroIntroduit = cobreValor
                        actualitzarResultat()
                    }
                }
                //el mateix amb plata
                "Plata" -> {
                    if (plataValor == null) {
                        botonsmaterials { valor -> plataValor = valor }
                    } else {
                        numeroIntroduit = plataValor
                        actualitzarResultat()
                    }
                }
                //el mateix amb platino
                "Platino" -> {
                    if (platinoValor == null) {
                        botonsmaterials { valor -> platinoValor = valor }
                    } else {
                        numeroIntroduit = platinoValor
                        actualitzarResultat()
                    }
                }
            }
        }
    }
    fun botonsmaterials(valorMaterial: (Double) -> Unit) {
        //contrueix el dialog
        val builder = AlertDialog.Builder(this)
        //li poso el titol amb els idiomes i el material
        builder.setTitle("${getString(R.string.introduce_valor)} $MaterialSeleccionat")
        //creo el input
        val input = EditText(this)
        //que el input sigui de tipus decimal
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("${getString(R.string.btn_ok)}") { dialog, _ ->
            //agafa el valor introduit
            val textIntroduit = input.text.toString()
            //converteix el text a double
            val valorIntroduit = textIntroduit.toDoubleOrNull()
            //si no es null
            if (valorIntroduit != null) {
                //el valor del material es el que ha posat
                valorMaterial(valorIntroduit)
                numeroIntroduit = valorIntroduit
                //s'actualitza el text de grams
                actualitzarTextGrams(valorIntroduit)
                //actualitza el resultat
                actualitzarResultat()
            }
            //tanca el dialog
            dialog.dismiss()
        }
        //tanca el dialog amb el boto de cancelar
        builder.setNegativeButton("${getString(R.string.btn_cancelar)}") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    fun actualitzarTextGrams(valor: Double) {
        //actualitza el text amb el valor que li poses al cridar la funcio i amb els text dels strings i el nom del material
        textgrams.text = "$valor ${getString(R.string.precio_por_gramo)} $MaterialSeleccionat"
    }
    fun actualitzarResultat() {
        numeroIntroduit?.let {
            //agafa el valor i canvio la coma per el punt per fer el calcul i es pasa a double, si al passarho a double falla es posa en 0.0
            val valorPreu = textpreu.text.toString().replace(',', '.').toDoubleOrNull() ?: 0.0
            //fa la multiplicacio
            val valorResultat = it * valorPreu
            //mostra el resultat pero canviant el punt per la coma
            textresultat.text = valorResultat.toString().replace('.', ',')
        }
    }
}