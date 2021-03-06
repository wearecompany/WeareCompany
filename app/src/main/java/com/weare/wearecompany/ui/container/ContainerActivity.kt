package com.weare.wearecompany.ui.container

import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.ActivityContainerBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail.EstimateDatailActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.home.oneclick.OneClickActivity
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import timber.log.Timber


class ContainerActivity : BaseActivity<ActivityContainerBinding>(
    R.layout.activity_container,
    //ContainerViewModel::class
), View.OnClickListener {

    var reservation:Int = -1
    var payment:Int = -1
    var move_data = ""
    var type_1 = ""
    var type_2 = ""
    var tutorial = 0
    var max = 0
    var min = 0
    var temp = 0
    var gcd = 0

    lateinit var host : NavHostFragment


    lateinit var fadeInAnim: Animation
    lateinit var fadeoutAnim: Animation

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private val appUpdatedListener: InstallStateUpdatedListener by lazy {
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(installState: InstallState) {
                when {
                    installState.installStatus() == InstallStatus.DOWNLOADED -> popupSnackbarForCompleteUpdate()
                    installState.installStatus() == InstallStatus.INSTALLED -> appUpdateManager.unregisterListener(
                        this
                    )
                    else -> Timber.d(
                        "InstallStateUpdatedListener: state: %s",
                        installState.installStatus()
                    )
                }
            }
        }
    }

    companion object {
        const val MY_REQUEST_CODE = 700
    }

    override fun onCreate() {

        reservation = intent.getIntExtra("reservation",0)
        payment = intent.getIntExtra("payment",0)
        move_data = intent.getStringExtra("move").toString()
        mViewDataBinding.bottomNavigation.background = null
        mViewDataBinding.bottomNavigation.menu.getItem(2).isEnabled = false
        mViewDataBinding.mainRequest.setOnClickListener(this)

        host = supportFragmentManager.findFragmentById(R.id.navHostfragment) as NavHostFragment

        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadin)
        fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout)

        val disp : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(disp) // in case of Activity
/* val display = activity!!.windowManaver.defaultDisplay */ // in case of Fragment
        //val size = Point()
        //display.getRealSize(size) // or getSize(size)
        val width = disp.widthPixels
        val height = disp.heightPixels

        if (width < height) {
            max = width
            min = height
        } else {
            max = height
            min = width
        }

        while (max/min != 0) {
            temp = max / min
            max = min
            min = temp
        }

        gcd = min

        var bundle =  intent.extras
        if (bundle != null) {
            type_1 = bundle["type_1"].toString()
            type_2 = bundle["type_2"].toString()
        }
        checkForAppUpdate()
        kakaolink()
        dunamLink()
        moveExpert()

        val bundlee = bundleOf("type_1" to type_1, "type_2" to type_2)
        NavigationUI.setupWithNavController(mViewDataBinding.bottomNavigation, host.navController)


        mViewDataBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    host.navController.navigate(R.id.main)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_expter -> {
                    Toast.makeText(this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    /*if (MyApplication.prefs.getString("user_idx", "") != "") {
                        host.navController.navigate(R.id.expter)
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        Toast.makeText(this, "???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
                    }*/
                }
                R.id.menu_estimate -> {
                    var studioIntent = Intent(this, ListContainerActivity::class.java)
                    studioIntent.putExtra("num", 0)
                    startActivity(studioIntent)
                    //Toast.makeText(this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    //host.navController.navigate(R.id.chat)
                    //return@setOnNavigationItemSelectedListener true
                    /*var newIntent = Intent(this, ListContainerActivity::class.java)
                    newIntent.putExtra("num", 8)
                    startActivity(newIntent)*/
                    //host.navController.navigate(R.id.estimate)
                    //return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_mypage -> {
                    if (MyApplication.prefs.getString("user_idx", "") != "") {
                        host.navController.navigate(R.id.test_mypage)
                        return@setOnNavigationItemSelectedListener true
                    } else {
                        host.navController.navigate(R.id.not_mypage)
                        return@setOnNavigationItemSelectedListener true
                    }

                }
            }
            false
        }



        if (type_1 == "0") {
            host.navController.navigate(R.id.main,bundlee)
        } else if (type_1 == "1") {
            host.navController.navigate(R.id.main,bundlee)
        }

        if (reservation == 1) {
            Toast.makeText(this,"?????? ??????", Toast.LENGTH_SHORT).show()
            host.navController.navigate(R.id.test_mypage)
            mViewDataBinding.bottomNavigation.selectedItemId = R.id.menu_mypage
        }

        if (payment == 1) {
            //host.navController.navigate(R.id.expter)
            val bundle = Bundle()
            bundle.putString("payment", "ok")
            //mViewDataBinding.bottomNavigation.selectedItemId = R.id.menu_expter
        }
    }

    private fun dunamLink() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener(this)
            { pendingDynamicLinkData ->
                var deeplink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deeplink = pendingDynamicLinkData.link
                }
                if (deeplink != null) {
                    val key2 = deeplink.getQueryParameter("item_idx")
                    val key3 = deeplink.getQueryParameter("item_type")
                    Toast.makeText(this, deeplink.toString(), Toast.LENGTH_SHORT).show()
                    when (key3) {
                        "0" -> {
                            var newIntent = Intent(this@ContainerActivity, DatailActivity::class.java)
                            newIntent.putExtra("idx", key2)
                            startActivity(newIntent)
                        }
                        "1" -> {

                        }
                        "2" -> {
                            var newIntent = Intent(this@ContainerActivity, ModelActivity::class.java)
                            newIntent.putExtra("expert_idx", key2)
                            startActivity(newIntent)
                        }
                        "2" -> {
                            var newIntent = Intent(this@ContainerActivity, DatailActivity::class.java)
                            newIntent.putExtra("idx", key2)
                            startActivity(newIntent)
                        }
                    }
                } else {
                }
            }
            .addOnFailureListener(this) { e ->
                Log.w(
                    "ContainerActivity",
                    "getDynamicLink:onFailure",
                    e
                )
            }
    }

    private fun moveExpert() {
        if (move_data != "" && move_data != "null") {
            var studioIntent = Intent(this, ListContainerActivity::class.java)
            studioIntent.putExtra("num", 0)
            startActivity(studioIntent)
            move_data = ""
        }
    }

    private fun checkForAppUpdate() {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                try {
                    val installType = when {
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> AppUpdateType.FLEXIBLE
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> AppUpdateType.IMMEDIATE
                        else -> null
                    }
                    if (installType == AppUpdateType.FLEXIBLE) appUpdateManager.registerListener(
                        appUpdatedListener
                    )

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        installType!!,
                        this,
                        MY_REQUEST_CODE
                    )
                } catch (e: IntentSender.SendIntentException) {
                }
            }
        }
    }


    private fun kakaolink() {
        if (intent.action === Intent.ACTION_VIEW) {
            val data = intent.data!!.getQueryParameter("data")
              // setAndroidExecutionParams?????? ???????????? ??????
            val key2 = intent.data!!.getQueryParameter("item_idx")
            val key3 = intent.data!!.getQueryParameter("item_type")
            when (key3) {
                "0" -> {
                    var newIntent = Intent(this@ContainerActivity, DatailActivity::class.java)
                    newIntent.putExtra("idx", key2)
                    startActivity(newIntent)
                }
                "1" -> {

                }
                "2" -> {
                    var newIntent = Intent(this@ContainerActivity, ModelActivity::class.java)
                    newIntent.putExtra("expert_idx", key2)
                    startActivity(newIntent)
                }
                "2" -> {
                    var newIntent = Intent(this@ContainerActivity, DatailActivity::class.java)
                    newIntent.putExtra("idx", key2)
                    startActivity(newIntent)
                }
            }

            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                MaterialAlertDialogBuilder(this)
                    .setPositiveButton("ok") { _, _ ->
                    }
                    .setMessage("??????????????? ???????????? ??????????????? ????????????.")
                    .show()
            }
        }
        when(resultCode){
            3001 -> {
                //host = supportFragmentManager.findFragmentById(R.id.navHostfragment) as NavHostFragment
                host.navController.navigate(R.id.expter)
                //mViewDataBinding.bottomNavigation.selectedItemId = R.id.menu_mypage
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            findViewById(R.id.container_layout),
            "??????????????? ?????????????????????.",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("????????????") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        snackbar.show()
    }


    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }

                //Check if Immediate update is required
                try {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        // If an in-app update is already running, resume the update.
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQUEST_CODE
                        )
                    }
                } catch (e: IntentSender.SendIntentException) {
                }
            }

        var metrics : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        Log.i( "?????????","display width" + metrics.widthPixels + ", heifht :" + metrics.heightPixels +", densityDpi : "+ metrics.densityDpi)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        moveTaskToBack(true)
        finish()
        android.os.Process.killProcess(android.os.Process.myPid())
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_request -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    MainManager.instance.certcheck(MyApplication.prefs.getString("user_idx", ""),complation = { responseStatus, check ->
                        when(responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                when(check) {
                                    0 -> {
                                        val testdi: tesdialog = tesdialog {
                                            when(it) {
                                                0 -> {

                                                }
                                                1 -> {
                                                    var newIntent = Intent(this, PhotoPaymentActivity::class.java)
                                                    startActivityForResult(newIntent, 9999)
                                                }
                                            }
                                        }
                                        testdi.show(supportFragmentManager, testdi.tag)
                                    }
                                    1 -> {
                                        val newIntent = Intent(this, OneClickActivity::class.java)
                                        startActivityForResult(newIntent, 3000)
                                    }
                                }
                            }
                        }
                    })

                    /*val requestFragment: UploadStepOnFragment = UploadStepOnFragment {
                        when (it) {
                            0 -> {

                            }
                            1 -> {
                                val one_requestFragment: UploadStepTwoFragment = UploadStepTwoFragment {
                                    when (it) {
                                        0 -> {
                                            clickmove(0)
                                        }
                                        1 -> {

                                            clickmove(1)
                                        }
                                        2 -> {
                                            clickmove(2)
                                        }
                                        3 -> {
                                            clickmove(3)
                                        }
                                    }
                                }
                                one_requestFragment.show(supportFragmentManager, one_requestFragment.tag)
                            }
                        }
                    }
                    requestFragment.show(supportFragmentManager, requestFragment.tag)*/
                } else {
                    Toast.makeText(this,"???????????? ?????????????????????.",Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    fun clickmove(num: Int) {
        var studioIntent = Intent(this, ListContainerActivity::class.java)
        studioIntent.putExtra("num", num)
        startActivity(studioIntent)
    }


}