package garcia.marco.androidchallenge.ui.screens.splash

import android.Manifest
import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import garcia.marco.androidchallenge.databinding.ActivitySplashBinding
import garcia.marco.androidchallenge.ui.custom.ErrorManager
import garcia.marco.androidchallenge.ui.screens.BaseActivity
import garcia.marco.androidchallenge.ui.screens.main.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun createView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        if (checkPermission() == true){
            if(networkState() == true){
                initApp()
            }else{
                ErrorManager.showMessage(this@SplashActivity, _message = "Para poder acceder a la aplicación necesita estar conectado a una red de datos móviles y/o WiFi")
            }
        } else {
            getPermissions()
        }

    }

    override fun collectFlows() {

    }

    private fun initApp(){
        binding.animationView.playAnimation()
        // Obtain the FirebaseAnalytics instance.
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                goToMainActivity()
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun checkPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this@SplashActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun getPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (checkPermission() == true){
                    initApp()
                } else {
                    getPermissions()
                }
            }
            else -> {
                getPermissions()
            }
        }
    }

    private fun networkState():Boolean{
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
            if (networkInfo.type === ConnectivityManager.TYPE_WIFI) {
                return true
            }
        } else {
            return false
        }
    }

    fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}