import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.provider.Settings

class MyLocationListener (private val context: Context,private val  callback: (Location?)-> Unit):LocationListener{
    override fun onLocationChanged(location: Location) {
        callback(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        AlertDialog.Builder(context).setTitle("location disabled")
            .setMessage("please active la location").setPositiveButton("Enable", { _, _ ->
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        })
            .setNegativeButton("cancel", null)
            .show()

    }

}