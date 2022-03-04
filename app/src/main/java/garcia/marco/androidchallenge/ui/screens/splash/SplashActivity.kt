package garcia.marco.androidchallenge.ui.screens.splash

import android.animation.Animator
import android.content.Intent
import garcia.marco.androidchallenge.databinding.ActivitySplashBinding
import garcia.marco.androidchallenge.ui.screens.BaseActivity
import garcia.marco.androidchallenge.ui.screens.main.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun createView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init animation
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                // Animation end
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

    override fun collectFlows() {

    }

    fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}