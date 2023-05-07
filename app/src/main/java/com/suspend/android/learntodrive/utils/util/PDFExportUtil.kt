
import android.app.Dialog
import android.content.Context
import android.content.Context.PRINT_SERVICE
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import com.hendrix.pdfmyxml.PdfDocument
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.ui.question.ListAdapterAnswer
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.displayDescription
import com.suspend.android.learntodrive.utils.extension.hide
import com.suspend.android.learntodrive.utils.extension.loadImage
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.show
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.start
import com.suspend.android.learntodrive.utils.extension.toDateTimeFilePDF
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.E


fun Question.toPagePDF(context: Context): AbstractViewRenderer {

    val page: AbstractViewRenderer =
        object : AbstractViewRenderer(context, R.layout.dl_view_question) {
            val container = view.findViewById<View>(R.id.container_question)
            override fun initView(view: View) {
                val listAdapterAnswer by lazy {
                    ListAdapterAnswer(
                        onClick = { },
                        showAnswerFailed = true,
                        alwaysShowCorrectAnswer = true
                    )
                }
                this.let {
                }


                container.findViewById<RecyclerView>(R.id.recycler_view_options).adapter =
                    listAdapterAnswer

                fun showImageForQuestionHasImage(question: Question) {
                    if (!question.image.isNullOrEmpty()) {
                        container.findViewById<ImageView>(R.id.image_view_question).apply {
                            val path = Constant.PATH.QUESTION + question.image
                            loadImage(path)
                            show()
                        }
                        container.findViewById<View>(R.id.view_item).hide()
                    } else {
                        container.findViewById<ImageView>(R.id.image_view_question).hide()
                        container.findViewById<View>(R.id.view_item).show()
                    }
                }
                // hiển thị câu hỏi
                this@toPagePDF.let { _question ->
                    container.findViewById<CardView>(R.id.layout_card_view_description).isVisible =
                        _question.description.isNullOrEmpty().not()
                    container.findViewById<TextView>(R.id.text_view_question).text =
                        "Mã ${_question.id} :\n${_question.content}"
                    container.findViewById<TextView>(R.id.text_view_description)
                        .displayDescription(_question.description)
                    // test thì không hiệ
                    listAdapterAnswer.submitList(_question.answer)
                    showImageForQuestionHasImage(_question)

                }
            }
        }
    page.setReuseBitmap(true)
    return page
}


fun genearete(context: Context) {
    val doc = PdfDocument(context)


    /* val page: AbstractViewRenderer = object : AbstractViewRenderer(context, R.layout.test) {
         private var _text: String? = null
         fun setText(text: String?) {
             _text = text
         }

         override fun initView(view: View) {
             val tv_hello = view.findViewById<View>(R.id.text_view_test) as TextView
             tv_hello.text = _text
         }
     }

 // you can reuse the bitmap if you want

 // you can reuse the bitmap if you want
     page.isReuseBitmap = true
 // add as many pages as you have

 // add as many pages as you have
     doc.addPage(page)*/



    doc.setRenderWidth(2115)
    doc.setRenderHeight(1500)
    doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT)
    doc.setProgressTitle(R.string.title_name_test)
    doc.setProgressMessage(R.string.title_name_test)
    doc.setFileName("test")
    doc.setSaveDirectory(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS))
    doc.setInflateOnMainThread(false)
    doc.setListener(object : PdfDocument.Callback {
        override fun onComplete(file: File?) {
            Log.e(PdfDocument.TAG_PDF_MY_XML, "Complete")
        }

        override fun onError(e: Exception?) {
            Log.e(PdfDocument.TAG_PDF_MY_XML, e?.message.toString())
        }

    })

    doc.createPdf(context)

}

fun gen(context: ComponentActivity, test: TestEntity, view: View) {

    val dialog = Dialog(context)
    dialog.start(false)
    val pages = test.questions?.map { inflateQuestionToView(context, it) }

    val filename = "BaiThiMa_${test.id}_${test.createdAt?.toDateTimeFilePDF()}"

    PdfGenerator.Builder()
        .setContext(context)
        .fromViewSource()
        .fromViewList(pages)
        //.fromView(view)
        .setFileName(filename)
        .setFolderNameOrPath(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path)
        .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
        .build(object : PdfGeneratorListener() {
            override fun onFailure(failureResponse: FailureResponse) {
                super.onFailure(failureResponse)
                dialog.dismiss()
                context.showToast("Xuất thất bại !")
            }

            override fun showLog(log: String) {
                super.showLog(log)
            }

            override fun onStartPDFGeneration() {
                /*When PDF generation begins to start*/

            }

            override fun onFinishPDFGeneration() {
                /*When PDF generation is finished*/
                dialog.dismiss()

            }

            override fun onSuccess(response: SuccessResponse) {
                super.onSuccess(response)
                dialog.dismiss()
                context.showToast("Xuất thành công. Đường dẫn ! ${response.path}")
            }
        })
}


fun genIText(context: Context, test: TestEntity, view: View) {


    val filename = "BaiThiMa_${test.id}_${test.createdAt?.toDateTimeFilePDF()}.pdf"
    val outputFile = File(context.getExternalFilesDir(null), filename)
    val pages = test.questions?.map { inflateQuestionToView(context,it) }


    val pdfDocument = PdfDocument(context)

     fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        view.background?.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

   /// pages?.forEach { pdfDocument.addPage(getBitmapFromView(it)) }
    pdfDocument.addPage(object :AbstractViewRenderer(context, pages!!.first()){
        override fun initView(p0: View?) {
            TODO("Not yet implemented")
        }

    })

  /*  pdfDocument.setRenderWidth(2115)
    pdfDocument.setRenderHeight(1500)*/
    pdfDocument.setOrientation(PdfDocument.A4_MODE.PORTRAIT)
    pdfDocument.setInflateOnMainThread(true)

    pdfDocument.setFileName(filename)
    pdfDocument.setSaveDirectory(context.getExternalFilesDir("PDF"))
    pdfDocument.setListener(object : PdfDocument.Callback {
        override fun onComplete(file: File) {
            Log.e(PdfDocument.TAG_PDF_MY_XML, "Complete")
        }

        override fun onError(e: java.lang.Exception) {
            Log.e(PdfDocument.TAG_PDF_MY_XML, e.toString())
        }
    })





    pdfDocument.createPdf(context)

}


fun inflateQuestionToView(context: Context, question: Question): View {

    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val root = inflater.inflate(R.layout.dl_view_question, null)
    val listAdapterAnswer by lazy {
        ListAdapterAnswer(
            onClick = { },
            showAnswerFailed = true,
            alwaysShowCorrectAnswer = true
        )
    }

    root.findViewById<RecyclerView>(R.id.recycler_view_options).adapter =
        listAdapterAnswer

    fun showImageForQuestionHasImage(question: Question) {
        if (!question.image.isNullOrEmpty()) {
            root.findViewById<ImageView>(R.id.image_view_question).apply {
                val path = Constant.PATH.QUESTION + question.image
                show()
                loadImage(path)
                logError(message = "show imAGE")

            }
            root.findViewById<View>(R.id.view_item).hide()
        } else {
            root.findViewById<ImageView>(R.id.image_view_question).hide()
            root.findViewById<View>(R.id.view_item).show()
            logError(message = "hide imAGE")
        }
    }
    // hiển thị câu hỏi
    question.let { _question ->
        root.findViewById<CardView>(R.id.layout_card_view_description).isVisible =
            _question.description.isNullOrEmpty().not()
        root.findViewById<TextView>(R.id.text_view_question).text =
            "Mã ${_question.id} :\n${_question.content}"
        root.findViewById<TextView>(R.id.text_view_description)
            .displayDescription(_question.description)
        // test thì không hiệ
        listAdapterAnswer.submitList(_question.answer)
        showImageForQuestionHasImage(_question)

    }
    return root.rootView
}

/*fun gpt(context: ComponentActivity, test: TestEntity){
    val document = com.itextpdf.text.pdf.PdfDocument().;
    val pageInfo: android.graphics.pdf.PdfDocument.PageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(
        Resources.getSystem().getDisplayMetrics().widthPixels,
        Resources.getSystem().getDisplayMetrics().heightPixels, 25
    ).create()
    val page: PdfDocument.Page = document.startPage(pageInfo)
}*/

fun a(context: Context, question: Question) {
    val printAttrs = PrintAttributes.Builder()
        .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
        .setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
        .setResolution(PrintAttributes.Resolution("YOUR_ID", PRINT_SERVICE, 300, 300))
        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        .build()
    val document = PrintedPdfDocument(context, printAttrs)
    val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(
        Resources.getSystem().getDisplayMetrics().widthPixels,
        Resources.getSystem().getDisplayMetrics().heightPixels, 1
    ).create()
    val page = document.startPage(pageInfo)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.dl_view_question, null)
//set text cho Text View trên View

    // create(view, question)

    val measureWidth = View.MeasureSpec.makeMeasureSpec(page.canvas.width, View.MeasureSpec.EXACTLY)
    val measuredHeight =
        View.MeasureSpec.makeMeasureSpec(page.canvas.height, View.MeasureSpec.EXACTLY)
    view.measure(measureWidth, measuredHeight)
    view.layout(0, 0, page.canvas.width, page.canvas.height)
    view.draw(page.canvas)
    document.finishPage(page)
    try {
        val pdfDirPath = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "pdf")
        pdfDirPath.mkdirs()
        val file = File(pdfDirPath, "pdfsend.pdf")
        var os = FileOutputStream(file)
        document.writeTo(os)
        document.close()
        os.close()
    } catch (e: IOException) {
        Toast.makeText(context, "Error generating file", Toast.LENGTH_LONG).show()
    }

}

