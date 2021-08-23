package com.weare.wearecompany.ui.bottommenu.mypage.informationchange

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.editUserManager
import com.weare.wearecompany.databinding.ActivityInformationChangeBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail.EstimateDatailPhotoUploadDialog
import com.weare.wearecompany.utils.BitmapUtils
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.dialog_edit_user.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class InformationChangeActivity : BaseActivity<ActivityInformationChangeBinding>(
    R.layout.activity_information_change
), View.OnClickListener, TextView.OnEditorActionListener {

    val REQ_CAMERA_PERMISSION = 1001;
    val REQ_STORAGE_PERMISSION = 1002;
    val REQ_IMAGE_CAPTURE = 1
    val REQ_GALLERY = 2
    lateinit var imagePath: String
    lateinit var imageFileName: String
    var photoUri: Uri? = null
    lateinit var f: File
    lateinit var uploadfile: File
    var imagebitmap: Bitmap? = null


    lateinit var textinput: String

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()

        mViewDataBinding.EditNicName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textinput = mViewDataBinding.EditNicName.text.toString()
                mViewDataBinding.TextCount.text = textinput.length.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                textinput = mViewDataBinding.EditNicName.text.toString()
                mViewDataBinding.TextCount.text = textinput.length.toString()
            }

        })


    }

    fun setup() {
        var fileuri: Uri = Uri.parse(intent.getStringExtra("image"))
        var fileName: String? = fileuri.lastPathSegment
        imageFileName = fileName!!
        photoUri = fileuri
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(MyApplication.instance)
            .load(intent.getStringExtra("image"))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(mViewDataBinding.EditImage)

        mViewDataBinding.EditNicName.setText(intent.getStringExtra("nicname"))
        var textfirst = mViewDataBinding.EditNicName.text.toString()
        mViewDataBinding.TextCount.text = textfirst.length.toString()
        mViewDataBinding.EditGallery.setOnClickListener(this)
        mViewDataBinding.EditNicName.setOnEditorActionListener(this)
        mViewDataBinding.editOk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.Edit_Gallery -> {
                val photodialog:EstimateDatailPhotoUploadDialog = EstimateDatailPhotoUploadDialog() {
                    when(it) {
                        0 -> {
                            selectCamera()
                        }
                        1 -> {
                            selectgallery()
                        }
                    }
                }
                photodialog.show(supportFragmentManager,photodialog.tag)
            }
            R.id.edit_ok -> {
                if (mViewDataBinding.EditNicName.text.toString().length < 2) {
                    mViewDataBinding.NicNameVerify.setText("별명은 최소 2글자 이어야 합니다.")
                    mViewDataBinding.NicNameVerify.setTextColor(Color.RED)
                } else if (mViewDataBinding.EditNicName.text.toString().length >= 2) {
                    editUserManager.instanc.data(
                        "name",
                        MyApplication.prefs.getString("user_idx", ""),
                        mViewDataBinding.EditNicName.text.toString(),
                        completion = { responseStatus, response ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    if (response == 200) {
                                        var drawable: BitmapDrawable =
                                            mViewDataBinding.EditImage.drawable as BitmapDrawable
                                        var listbitmap = drawable.bitmap
                                        uploadfile = BitmapUtils.glideToBitmap(
                                            this,
                                            listbitmap,
                                            imageFileName
                                        )
                                        //uploadfile = BitmapUtils.resizeBitmap(this, imagebitmap, imageFileName)
                                        editUserManager.instanc.profiletest(
                                            MyApplication.prefs.getString("user_idx", ""),
                                            mViewDataBinding.EditNicName.text.toString(),
                                            uploadfile,
                                            completion = { responseStatus, response ->
                                                when (responseStatus) {
                                                    RESPONSE_STATUS.OKAY -> {
                                                        onBackPressed()
                                                    }
                                                }
                                            })
                                    } else if (response == 202) {
                                        mViewDataBinding.NicNameVerify.setText("이미 사용중인 별명입니다.")
                                        mViewDataBinding.NicNameVerify.setTextColor(Color.RED)
                                    }
                                }
                            }
                        })
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_IMAGE_CAPTURE -> {
                    galleryAddPic()
                }
            }
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_GALLERY -> {
                    photoUri = data?.data  //갤러리 사진 불러오기/ 사진 선택 안했을시 경우 적용해야됨.
                    var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
                    Glide.with(MyApplication.instance)
                        .load(photoUri)
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(mViewDataBinding.EditImage)
                    var fileName: String? = photoUri!!.lastPathSegment
                    imageFileName = fileName!!
                    //mViewDataBinding.EditImage.setImageBitmap(resizeBitmap2(photoUri, 250))
                    try {

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun selectCamera() {
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQ_CAMERA_PERMISSION
            )
        } else { // 권한 있음
            var state = Environment.getExternalStorageState()
            if (TextUtils.equals(state, Environment.MEDIA_MOUNTED)) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //카메라 인텐트 생성
                intent.resolveActivity(this.packageManager)?.let {
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    photoFile?.let {
                        photoUri = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile
                        )
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(intent, REQ_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    private fun selectgallery() {
        var writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQ_STORAGE_PERMISSION
            )
        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, REQ_GALLERY)
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {

        // 이미지 파일 이름
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        imageFileName = ""
        imageFileName = "Wearecompany" + timeStamp + "_"

        // 이미지가 저장될 주소
        var storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString() + "/Wearecompany"
        )
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x$storageDir")
            storageDir.mkdirs()
        }
        Log.v("알림", "storageDir 존재함$storageDir")

        //빈 파일 생성
        storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString() + "/Wearecompany", System.currentTimeMillis().toString() + ".jpg"
        )
        imagePath = storageDir.absolutePath
        return storageDir
    }

    fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        val ei = ExifInterface(imagePath)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(MyApplication.instance)
            .load(photoUri)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(mViewDataBinding.EditImage)
        MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(f))?.let {
            //mViewDataBinding.EditImage.setImageBitmap(resizeBitmap(it, 250f, angle))
            //imagebitmap = resizeBitmap(it, 250f, angle)
            imagebitmap = resizeBitmap2(photoUri!!, 250)
        }
        mediaScanIntent.data = contentUri
        //this.sendBroadcast(mediaScanIntent)
        //val test = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(f))
        //mViewDataBinding.EditImage.setImageBitmap(test1)
    }

    private fun resizeBitmap2(uri: Uri?, resize: Int): Bitmap? {

        var resizeBitmap: Bitmap? = null
        var resizedBitmap: Bitmap? = null

        var options: BitmapFactory.Options = BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri!!),
                null,
                options
            ); // 1번

            var width: Int = options.outWidth;
            var height: Int = options.outHeight;
            var samplesize: Int = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            var bitmap: Bitmap? = BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번
            if (bitmap != null) {
                resizeBitmap = bitmap
            }
            var rotateMatrix: Matrix = Matrix()
            rotateMatrix.postRotate(90f); //-360~360
            resizedBitmap = resizeBitmap?.let {
                Bitmap.createBitmap(
                    it,
                    0,
                    0,
                    it.width,
                    it.height,
                    rotateMatrix,
                    true
                )
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        }
        return resizedBitmap

    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (v?.id == R.id.Edit_NicName && actionId == EditorInfo.IME_ACTION_DONE) {
            mViewDataBinding.NicNameVerify.visibility = View.VISIBLE
            if (textinput.length < 2) {
                mViewDataBinding.NicNameVerify.setText("별명은 최소 2글자 이어야 합니다.")
                mViewDataBinding.NicNameVerify.setTextColor(Color.RED)
            } else if (textinput.length >= 2) {
                editUserManager.instanc.data(
                    "name",
                    MyApplication.prefs.getString("user_idx", ""),
                    mViewDataBinding.EditNicName.text.toString(),
                    completion = { responseStatus, response ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                if (response == 200) {
                                    mViewDataBinding.NicNameVerify.setText("사용할 수 있는 별명입니다.")
                                    mViewDataBinding.NicNameVerify.setTextColor(Color.parseColor("#6d34f3"))
                                } else if (response == 202) {
                                    mViewDataBinding.NicNameVerify.setText("이미 사용중인 별명입니다.")
                                    mViewDataBinding.NicNameVerify.setTextColor(Color.RED)
                                }
                            }
                        }
                    })
            }
        }
        return false
    }
}