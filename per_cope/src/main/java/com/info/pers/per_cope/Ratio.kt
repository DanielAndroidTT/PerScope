package com.info.pers.per_cope

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Ratio(private val activity: AppCompatActivity) {
    private val lk =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            q()
        }

    private val ratioHandler: RatioHandler = SimpleRatioHandler

    private var re: String = ""
    private var ht = ""

    fun startRatio(
        mainActivity: Class<out Activity>,
        data: String,
        key: String,
    ) {
        activity.apply {
            if (!isTaskRoot
                && intent?.hasCategory(Intent.CATEGORY_LAUNCHER) == true
                && intent.action?.equals(Intent.ACTION_MAIN) == true
            ) {
                finish()
                return
            }
        }

        this.re = data
        this.ht = key

        ratioHandler.rg(mainActivity)

        with(activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    q()
                } else {
                    lk.launch(permission)
                }
            } else q()
        }
    }

    private fun q() {
        if (re.isBlank() || ht.isBlank()) return

        CoroutineScope(Dispatchers.Main).launch {
            val t = ratioHandler.e(re, ht)

            val td = ratioHandler.b(activity)

            if (t?.s == true || !ratioHandler.ku(
                    activity
                ) || td.second
            ) {
                ratioHandler.ht(activity)
                return@launch
            }

            if(t == null){
                delay(1500L)
                q()
                return@launch
            }

            ratioHandler.gew(activity, t.r)

            val lp = if (!td.first.isNullOrBlank()) td.first
            else ratioHandler.fg(activity, t)

            val ds = Intent(activity, SimplePolicy::class.java)

            ds.apply {
                putExtra("data", lp)
                putExtra("ex", t.q)
            }

            activity.startActivity(ds)
            activity.finish()
        }

    }

}