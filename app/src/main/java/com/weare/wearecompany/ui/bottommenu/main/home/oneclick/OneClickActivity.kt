package com.weare.wearecompany.ui.bottommenu.main.home.oneclick

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.onclickManager
import com.weare.wearecompany.databinding.ActivityOneClickBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail.EstimateDatailPhotoUploadDialog
import com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail.EstimateDatailRecyclerViewAdapter
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.join.PersonaActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OneClickActivity:BaseActivity<ActivityOneClickBinding>(
    R.layout.activity_one_click
),View.OnClickListener {

    val REQ_CAMERA_PERMISSION = 1001
    val REQ_STORAGE_PERMISSION = 1001

    private val CAMERA_IMAGE = 1
    private val GALLERY_IMAGE = 2
    lateinit var userIdx:String

    lateinit var imagePath: String
    lateinit var photoUri: Uri
    lateinit var imageFileName: String
    lateinit var expert_type: ArrayList<Int>

    private var photoList = ArrayList<Uri>()
    private var dataList = ArrayList<Uri>()
    private var datafileList = ArrayList<File>()
    private var filenameList = ArrayList<String>()
    private lateinit var dataAdapter: EstimateDatailRecyclerViewAdapter

    private lateinit var storageDir: File

    override fun onCreate() {

        userIdx = MyApplication.prefs.getString("user_idx","")

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        bindsetup()
        setUp()
    }

    private fun bindsetup() {
        mViewDataBinding.requestImageUpload.setOnClickListener(this)
        mViewDataBinding.oneClickOk.setOnClickListener(this)
        mViewDataBinding.oneclickPersonal.setOnClickListener(this)
    }

    private fun setUp() {

        mViewDataBinding.oneClickModel.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }

        mViewDataBinding.oneClickTrip.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }

        mViewDataBinding.oneClickPhoto.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }

        mViewDataBinding.oneClickStudio.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }

        mViewDataBinding.oneClickConsent.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                button.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }

        dataAdapter = EstimateDatailRecyclerViewAdapter(this, dataList)
        mViewDataBinding.oneclickImageRecyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        mViewDataBinding.oneclickImageRecyclerview.adapter = dataAdapter
        dataAdapter.setItemClickListener(object :
            EstimateDatailRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, Item: Uri) {
                val file = File(this@OneClickActivity.cacheDir, filenameList[position])
                if (file.exists()) {
                    file.delete()
                }
                dataAdapter.removeItem(position)
                mViewDataBinding.oneclickImageCount.setText(dataAdapter.getSize().toString())
                photoList.removeAt(position)
                filenameList.removeAt(position)
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.request_image_upload -> {
                val photodialog: EstimateDatailPhotoUploadDialog = EstimateDatailPhotoUploadDialog() {
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
            R.id.oneclick_personal -> {
                var newIntent = Intent(this, PersonaActivity::class.java)
                newIntent.putExtra("type", "?type=1")
                newIntent.putExtra("num", 1)
                startActivity(newIntent)
            }
            R.id.one_click_ok -> {
                expert_type = ArrayList()

                if (mViewDataBinding.oneClickModel.isChecked) {
                    expert_type.add(2)
                }
                if (mViewDataBinding.oneClickTrip.isChecked) {
                    expert_type.add(3)
                }
                if (mViewDataBinding.oneClickPhoto.isChecked) {
                    expert_type.add(1)
                }
                if (mViewDataBinding.oneClickStudio.isChecked) {
                    expert_type.add(0)
                }

                if (expert_type.size == 0) {
                    Toast.makeText(this,"최소 1개는 선택해야 됩니다.",Toast.LENGTH_SHORT).show()
                } else if (mViewDataBinding.oneClickContents.text.toString() == ""){
                    Toast.makeText(this,"요청사항을 적어주세요.",Toast.LENGTH_SHORT).show()
                } else if (mViewDataBinding.oneClickMoney.text.toString() == "" || mViewDataBinding.oneClickMoney.text.toString() == "0") {
                    Toast.makeText(this,"요청금액을 적어주세요.",Toast.LENGTH_SHORT).show()
                } else if (!mViewDataBinding.oneClickConsent.isChecked){
                    Toast.makeText(this,"개인정보수집 이용 동의를 해주세요",Toast.LENGTH_SHORT).show()
                } else {
                    mViewDataBinding.oneClickLoding.visibility = View.VISIBLE
                    if (photoList.size != 0) {
                        for (i in 1..photoList.size) {
                            datafileList.add(imageReSize(photoList[i - 1], 500, filenameList[i - 1]))
                        }
                    }

                    onclickManager.instance.oneclickupload(userIdx,expert_type,mViewDataBinding.oneClickContents.text.toString(),mViewDataBinding.oneClickMoney.text.toString().toInt(),datafileList,completion = {responseStatus ->
                        when(responseStatus) {
                            RESPONSE_STATUS.OKAY -> {

                                var newIntent =
                                    Intent(this, ContainerActivity::class.java)
                                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                newIntent.putExtra("reservation", 1)
                                startActivity(newIntent,)
                                finish()
                            }
                        }
                    })
                }



            }
        }
    }

    private fun selectCamera() {
        var permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
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
                        startActivityForResult(intent, CAMERA_IMAGE)
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
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            //intent.type = "image/*"
            startActivityForResult(intent, GALLERY_IMAGE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {

        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        // 이미지 파일 이름
        imageFileName = ""
        imageFileName = "onclick" + "_" + timeStamp

        // 이미지가 저장될 주소
        storageDir = File(
            this.getCacheDir(), "/Wearecompany/"
        )
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x$storageDir")
            storageDir.mkdirs()
        }
        Log.v("알림", "storageDir 존재함$storageDir")

        //빈 파일 생성
        storageDir = File(
            this.cacheDir, "/Wearecompany/$imageFileName"
        )

        imagePath = storageDir.absolutePath
        return storageDir
    }

    private fun imageReSize(uri: Uri, resize: Int, fileName: String?): File {

        var resizeBitmap: Bitmap? = null
        var resizedBitmap: Bitmap? = null
        var RotationBitmap: Bitmap? = null

        var options: BitmapFactory.Options = BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ); // 1번

            var width: Int = options.outWidth
            var height: Int = options.outHeight
            var dwidth: Int = options.outWidth
            var dheight: Int = options.outHeight
            var samplesize: Int = 1

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2
                height /= 2
                samplesize *= 2
            }

            options.inSampleSize = samplesize;
            var bitmap: Bitmap? = BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번

            width = options.outWidth
            height = options.outHeight

            if (bitmap != null) {
                resizeBitmap = bitmap
            }
            if (dwidth < dheight) {
                if (dwidth > 700) {
                    dwidth = 700
                    dheight = (dwidth.toFloat() / width.toFloat() * height).toInt()
                }
            } else if (dheight < dwidth) {
                if (dheight > 700) {
                    dheight = 700
                    dwidth = (dheight.toFloat() / height.toFloat() * width).toInt()
                }
            } else if (dheight == dwidth) {
                dwidth = 700
                dheight = 700
            }

            if (bitmap != null) {
                resizeBitmap = bitmap
            }

            var res: InputStream = contentResolver.openInputStream(uri)!!
            var exif: ExifInterface = ExifInterface(res)
            res.close()

            var orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            //사진 회전
            var rotateMatrix: Matrix = Matrix()
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotateMatrix.postRotate(90f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotateMatrix.postRotate(180f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotateMatrix.postRotate(270f)
            }

            resizedBitmap = resizeBitmap?.let {
                Bitmap.createScaledBitmap(it, dwidth, dheight, true)
            }

            RotationBitmap = resizedBitmap?.let {
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

        val file = File(this.cacheDir, fileName)
        try {
            val outStream = ByteArrayOutputStream()

            RotationBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.flush()
            fo.close()
        } catch (e: Exception) {
        }
        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (photoList.size < 6) {
                        dataAdapter.addItem(photoUri)
                        photoList.add(photoUri)
                        filenameList.add(imageFileName)
                        mViewDataBinding.oneclickImageCount.setText(dataAdapter.getSize().toString())
                    } else {
                        Toast.makeText(this, "사진을 최대로 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            GALLERY_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {

                    if (data?.clipData != null) { // 사진 여러개 선택한 경우
                        val count = data.clipData!!.itemCount
                        if (photoList.size < 6) {
                            val photo_range = 6 - photoList.size
                            if (photo_range < count ) {
                                Toast.makeText(this, "$photo_range 개 사진까지 추가 가능합니다.", Toast.LENGTH_SHORT).show()
                            } else if (photo_range >= count) {
                                for (i in 0 until count) {
                                    photoUri = data.clipData!!.getItemAt(i).uri
                                    imageFileName = ""
                                    imageFileName = photoUri?.lastPathSegment!!
                                    dataAdapter.addItem(photoUri)
                                    photoList.add(photoUri)
                                    filenameList.add(imageFileName)
                                    mViewDataBinding.oneclickImageCount.setText(dataAdapter.getSize().toString())
                                }
                            }
                        } else {
                            Toast.makeText(this, "사진을 최대로 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {    //단일 선택
                        if (photoList.size < 6) {
                            data?.data?.let { uri ->
                                photoUri = data?.data!!
                                imageFileName = ""
                                imageFileName = photoUri?.lastPathSegment!!
                                dataAdapter.addItem(photoUri)
                                photoList.add(photoUri)
                                filenameList.add(imageFileName)
                                mViewDataBinding.oneclickImageCount.setText(dataAdapter.getSize().toString())
                            }
                        } else {
                            Toast.makeText(this, "사진을 최대로 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

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
}