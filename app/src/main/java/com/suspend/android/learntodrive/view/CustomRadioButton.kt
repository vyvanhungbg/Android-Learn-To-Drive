package com.suspend.android.learntodrive.view


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.utils.extension.logError
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val TAG = "CustomRadioButton"

class CustomRadioButton : AppCompatRadioButton {

    private var _view: View? = null
    private var _textView: TextView? = null
    private var _imageView: ImageView? = null

    private val view: View get() = _view!!;
    private val imageView: ImageView get() = _imageView!!;
    private val textView: TextView get() = _textView!!;


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton)

        val text = a.getString(R.styleable.CustomRadioButton_text)
        val image = a.getDrawable(R.styleable.CustomRadioButton_image)
        setTextWith(text)
        setImageResource(image)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    fun setOnCheckedChangeWithAnotherRadio(radioButton2: RadioButton) {
        /* radioButton1.setOnCheckedChangeListener(OnCheckedChangeListener { compoundButton, b ->

         })*/
        try {
            this.setOnClickListener(OnClickListener { _: View? ->
                radioButton2.isChecked = this.isChecked.not()
            })
            radioButton2.setOnClickListener(OnClickListener { _: View? ->
                this.isChecked = radioButton2.isChecked.not()
            })
        } catch (ex: Exception) {
            logError(TAG, ex.message)
        }

    }

    private val requestListener = object : RequestListener<Bitmap?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap?>,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }


        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            imageView.setImageBitmap(resource)
            redrawLayout()
            return false
        }
    }

    fun setImageResource(resId: Drawable?) {

        Glide.with(getContext())
            .asBitmap()
            .load(resId)
            .centerCrop()
            .listener(requestListener)
            .submit()

    }

    fun setImageBitmap(bitmap: Bitmap?) {
        Glide.with(getContext())
            .asBitmap()
            .load(bitmap)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            dp2px(getContext(), 24),
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                )
            )
            .listener(requestListener)
            .submit()
    }

    // setText is a final method in ancestor, so we must take another name.
    fun setTextWith(resId: Int) {
        textView.setText(resId)
        redrawLayout()
    }

    fun setTextWith(text: CharSequence?) {
        textView.text = text
        redrawLayout()
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.custom_view_radio_button, null)
        _textView = view.findViewById(R.id.text_view)
        _imageView = view.findViewById(R.id.image_view)
        redrawLayout()
    }


    private fun redrawLayout() {
        view.isDrawingCacheEnabled = true
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight())
        view.buildDrawingCache(true)

        val bitmap: Bitmap = Bitmap.createBitmap(view.getDrawingCache())
        setCompoundDrawablesWithIntrinsicBounds(
            BitmapDrawable(getResources(), bitmap),
            null,
            null,
            null
        )
        view.setDrawingCacheEnabled(false)

    }

    private fun dp2px(context: Context, dp: Int): Int {
        return (dp * context.getResources().getDisplayMetrics().density) as Int
    }
}