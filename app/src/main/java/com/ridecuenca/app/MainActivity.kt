package com.ridecuenca.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fondo = LinearLayout(this)
        fondo.orientation = LinearLayout.VERTICAL
        fondo.gravity = Gravity.CENTER
        fondo.setPadding(28, 28, 28, 28)
        fondo.setBackgroundColor(Color.rgb(12, 25, 19))

        fun texto(contenido: String, tamaño: Float, color: Int) {
            val vista = TextView(this)
            vista.text = contenido
            vista.textSize = tamaño
            vista.setTextColor(color)
            vista.gravity = Gravity.CENTER
            vista.setPadding(12, 18, 12, 18)
            fondo.addView(vista)
        }

        texto("🚴 RIDE CUENCA", 32f, Color.rgb(170, 255, 0))
        texto("TU COMUNIDAD CICLISTA", 16f, Color.WHITE)
        texto("BICICLETAS  •  ACCESORIOS  •  RUTAS", 14f, Color.WHITE)
        texto("Explora bicicletas", 22f, Color.rgb(170, 255, 0))
        texto("Compra y venta", 20f, Color.WHITE)
        texto("Talleres y tiendas", 20f, Color.WHITE)
        texto("Rutas de Cuenca", 20f, Color.WHITE)
        texto("Mi actividad", 20f, Color.WHITE)

        setContentView(fondo)
    }
}
