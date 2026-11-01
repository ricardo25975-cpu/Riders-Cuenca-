package com.ridecuenca.app

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import org.json.JSONObject

class MainActivity : Activity(), LocationListener {
    private lateinit var web: WebView
    private lateinit var locationManager: LocationManager
    private var tracking = false
    private var lastLocation: Location? = null
    private var totalMeters = 0f
    private var startedAt = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webViewClient = WebViewClient()
        web.webChromeClient = WebChromeClient()
        web.addJavascriptInterface(Bridge(), "RideGPS")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    inner class Bridge {
        @JavascriptInterface fun start() { runOnUiThread { beginTracking() } }
        @JavascriptInterface fun stop() { runOnUiThread { endTracking() } }
    }

    private fun beginTracking() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 44)
            return
        }
        tracking = true
        totalMeters = 0f
        lastLocation = null
        startedAt = System.currentTimeMillis()
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1f, this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000L, 2f, this)
        } catch (_: Exception) { sendGps("GPS no disponible. Activa la ubicación del teléfono.", 0.0, 0.0, 0.0) }
    }

    private fun endTracking() {
        tracking = false
        try { locationManager.removeUpdates(this) } catch (_: Exception) {}
        val seconds = if (startedAt == 0L) 0L else (System.currentTimeMillis() - startedAt) / 1000
        sendGps("Entrenamiento pausado", 0.0, totalMeters / 1000.0, seconds.toDouble())
    }

    override fun onLocationChanged(location: Location) {
        if (!tracking) return
        val previous = lastLocation
        if (previous != null && location.accuracy < 35f) {
            val d = previous.distanceTo(location)
            if (d in 1f..100f) totalMeters += d
        }
        lastLocation = location
        val seconds = (System.currentTimeMillis() - startedAt) / 1000.0
        val speed = if (location.hasSpeed()) location.speed * 3.6 else 0.0
        sendGps("GPS activo", speed, totalMeters / 1000.0, seconds, if (location.hasAltitude()) location.altitude else 0.0)
    }

    private fun sendGps(status: String, speed: Double, km: Double, seconds: Double, altitude: Double = 0.0) {
        val data = JSONObject().put("status", status).put("speed", speed).put("km", km).put("seconds", seconds).put("altitude", altitude)
        runOnUiThread { web.evaluateJavascript("window.updateGPS(${data})", null) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 44 && grantResults.any { it == PackageManager.PERMISSION_GRANTED }) beginTracking()
        else sendGps("Permiso de ubicación denegado", 0.0, 0.0, 0.0)
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) { if (tracking) sendGps("Activa el GPS del teléfono", 0.0, totalMeters / 1000.0, 0.0) }
    @Deprecated("Deprecated in Android") override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}
