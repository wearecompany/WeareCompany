package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.reviewManager
import com.weare.wearecompany.databinding.ActivityReviewBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ReviewActivity:BaseActivity<ActivityReviewBinding>(
    R.layout.activity_review
),View.OnClickListener {

    private val REQ_CAMERA_PERMISSION = 1001
    val REQ_STORAGE_PERMISSION = 1001

    private val CAMERA_IMAGE = 1
    private val GALLERY_IMAGE = 2

    var photoUri: Uri? = null
    var imageFileName: String = ""
    var fileName: String? = null
    private lateinit var storageDir: File
    lateinit var imagePath: String

    private var reserve_idx: String = ""
    private var request_idx: String = ""
    private var request_log_idx: String = ""
    private var type: Int = -1
    private var uploadFile:File? = null

    private var photocheck = false
    private var grade = 1

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        type = intent.getIntExtra("type", 0)
        if (type == 0) {
            reserve_idx = intent.getStringExtra("reserve_idx").toString()
        } else if (type == 1) {
            request_idx = intent.getStringExtra("reserve_idx").toString()
            request_log_idx = intent.getStringExtra("reserve_idx").toString()
        }

        setup()

    }

    private fun setup() {

        mViewDataBinding.photoUpload.setOnClickListener(this)
        mViewDataBinding.reviewPhoto.setOnClickListener(this)
        mViewDataBinding.reviewItem0.setOnClickListener(this)
        mViewDataBinding.reviewItem1.setOnClickListener(this)
        mViewDataBinding.reviewItem2.setOnClickListener(this)
        mViewDataBinding.reviewItem3.setOnClickListener(this)
        mViewDataBinding.reviewItem4.setOnClickListener(this)
        mViewDataBinding.reviewOk.setOnClickListener(this)

        progressManager.instance.review(type,reserve_idx,request_idx,request_log_idx,completion = {responseStatus,arrayList ->
            when(responseStatus) {
                ESTIMATE.OKAY -> {
                    mViewDataBinding.expertUserNickname.text = arrayList[0].expert_user_nickname
                    mViewDataBinding.expertCategory.text = arrayList[0].expert_category
                    mViewDataBinding.expertTitle.text = arrayList[0].expert_title
                    var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

                    Glide.with(MyApplication.instance)
                        .load(arrayList[0].expert_thumbnail)
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(mViewDataBinding.expertThumbnail)
                }
            }
        })
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
                    var userphotoFile: File? = null
                    try {
                        userphotoFile = createImageFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    userphotoFile?.let {
                        photoUri = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            userphotoFile
                        )
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(intent, CAMERA_IMAGE)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        // 이미지 파일 이름
        imageFileName = ""
        imageFileName = "review_" + timeStamp + "_"
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
            startActivityForResult(intent, GALLERY_IMAGE)
        }
    }

    private fun camreaAddPic() {

        fileName = photoUri?.lastPathSegment
        //fileNameList.add(fileName!!)
        mViewDataBinding.reviewPhoto.visibility = View.VISIBLE
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(this)
            .load(imagePath)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(mViewDataBinding.reviewPhoto)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.photo_upload -> {
                var photoFragment : ReviewPhotoUploadDialog = ReviewPhotoUploadDialog(){
                    when(it) {
                        0 -> {
                            selectCamera()
                        }
                        1 -> {
                            selectgallery()
                        }
                    }
                }
                photoFragment.show(supportFragmentManager,photoFragment.tag)
            }
            R.id.review_photo -> {
                mViewDataBinding.reviewPhoto.visibility = View.GONE
                imageFileName = ""
                uploadFile = null
            }
            R.id.review_item_0 -> {
                grade = 1
                mViewDataBinding.reviewItem1.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem2.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem3.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem4.setImageResource(R.drawable.review_off)
            }
            R.id.review_item_1 -> {
                grade = 2
                mViewDataBinding.reviewItem1.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem2.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem3.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem4.setImageResource(R.drawable.review_off)
            }
            R.id.review_item_2 -> {
                grade = 3
                mViewDataBinding.reviewItem1.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem2.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem3.setImageResource(R.drawable.review_off)
                mViewDataBinding.reviewItem4.setImageResource(R.drawable.review_off)
            }
            R.id.review_item_3 -> {
                grade = 4
                mViewDataBinding.reviewItem1.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem2.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem3.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem4.setImageResource(R.drawable.review_off)
            }
            R.id.review_item_4 -> {
                grade = 5
                mViewDataBinding.reviewItem1.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem2.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem3.setImageResource(R.drawable.review_on)
                mViewDataBinding.reviewItem4.setImageResource(R.drawable.review_on)
            }
            R.id.review_ok -> {
                if (mViewDataBinding.reviewText.text?.isNotEmpty() == false){
                     Toast.makeText(this,"후기 내용을 작성해주세요.",Toast.LENGTH_SHORT).show()
                } else {
                    if (photoUri != null) {
                        uploadFile = imageReSize(photoUri!!, 700, imageFileName)
                    }
                    reviewManager.instance.upload(
                        type,reserve_idx,request_idx,request_log_idx,grade,mViewDataBinding.reviewText.text.toString(),uploadFile,completion = {responseStatus ->
                            when(responseStatus) {
                                ESTIMATE.OKAY -> {
                                    val file = File(this.cacheDir, imageFileName)
                                    if (file.exists()) {
                                        file.delete()
                                    }
                                    val intent = Intent()
                                    setResult(3004, intent)
                                    finish()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    camreaAddPic()
                }

            }
            GALLERY_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        photoUri = it.data!!
                        //갤러리 사진 불러오기/ 사진 선택 안했을시 경우 적용해야됨.
                        mViewDataBinding.reviewPhoto.visibility = View.VISIBLE
                        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
                        // 이미지 파일 이름
                        imageFileName = ""
                        imageFileName = "review_" + timeStamp + "_"

                        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
                        Glide.with(MyApplication.instance)
                            .load(photoUri)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.reviewPhoto)
                    }

                }


            }
        }
    }

    private fun imageReSize(uri: Uri, resize: Int, fileName: String?): File {

        var resizeBitmap: Bitmap? = null

        var resizedBitmap: Bitmap? = null

        var RotationBitmap: Bitmap? = null

        var options: BitmapFactory.Options = BitmapFactory.Options()
        try {
            BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ); // 1번

            var width: Int = options.outWidth;
            var height: Int = options.outHeight;
            var dwidth: Int = options.outWidth
            var dheight: Int = options.outHeight
            var samplesize: Int = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize += 1;
            }

            options.inSampleSize = samplesize
            var bitmap: Bitmap? = BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번


            width = options.outWidth
            height = options.outHeight
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

        val file = File(this.cacheDir, "/Wearecompany/$fileName")
        try {

            val outStream = ByteArrayOutputStream()

            RotationBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            file.createNewFile()
            if (file.exists()) {
                file.mkdirs()
            }
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.flush()
            fo.close()
        } catch (e: Exception) {

        }
        return file
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