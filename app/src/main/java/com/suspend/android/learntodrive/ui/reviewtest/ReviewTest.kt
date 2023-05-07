package com.suspend.android.learntodrive.ui.reviewtest

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.suspend.android.learntodrive.base.BaseFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentReviewTestBinding
import com.suspend.android.learntodrive.di.SHARED_PREFERENCES_TYPE
import com.suspend.android.learntodrive.model.Answer
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.toText
import com.suspend.android.learntodrive.ui.question.ListAdapterAnswer
import com.suspend.android.learntodrive.ui.test.ListAdapterCirclePositionQuestion
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.displayDescription
import com.suspend.android.learntodrive.utils.extension.getAutoNextSpeechSettings
import com.suspend.android.learntodrive.utils.extension.hide
import com.suspend.android.learntodrive.utils.extension.loadImage
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.openDialogSetting
import com.suspend.android.learntodrive.utils.extension.show
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.speech
import com.suspend.android.learntodrive.utils.extension.viewImage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

private const val TAG = "ReviewTest"

class ReviewTest : BaseFragment<FragmentReviewTestBinding>(FragmentReviewTestBinding::inflate) {
    override val viewModel by viewModel<ReviewTestViewModel>()

    private val tts: TextToSpeech by inject()

    private var autoNextSettings = false

    private val safeArgs: ReviewTestArgs by navArgs()

    private val sharedPreferencesSettings: SharedPreferences by inject(
        qualifier = named(
            SHARED_PREFERENCES_TYPE.SETTINGS
        )
    )

    private val listAdapterAnswer by lazy {
        ListAdapterAnswer(
            { ::onItemCheckedChanged },
            showAnswerFailed = true,
            alwaysShowCorrectAnswer = true
        )
    }

    private val listAdapterCirclePositionQuestion by lazy {
        ListAdapterCirclePositionQuestion(
            ::onClickRecyclerViewOverView,
            showQuestionFailed = true
        )
    }

    private val lístener = object : UtteranceProgressListener() {
        override fun onStart(p0: String?) {
            logError(TAG, "onStart${p0}")
        }

        override fun onDone(p0: String?) {
            logError(TAG, "onDone")
        }

        override fun onError(p0: String?) {
            logError(TAG, "onError")
        }

    }

    private var dialog: Dialog? = null


    override fun initData() {
        if (safeArgs.bundleItemTestEntity != null) {
            viewModel.getTestEntity(safeArgs.bundleItemTestEntity!!)
        } else {
            findNavController().popBackStack()
            context?.showToast(getString(R.string.text_error_review_test))
        }
        context?.let {
            dialog = Dialog(it)
        }
        autoNextSettings = sharedPreferencesSettings.getAutoNextSpeechSettings()
    }


    override fun initView() {
        binding.containerQuestion.textViewDescription.isVisible = true
        //setPanelSlidingListener()
        initViewQuestion()
        setUpRecyclerViewOverView()
        setSubmenu()


        binding.apply {
            imageViewNextQuestion.setOnClickListener {
                viewModel.nextQuestion()
            }
            imageViewPreviousQuestion.setOnClickListener {
                viewModel.previousQuestion()
            }
            imageViewExit.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.voiceSpeech.observe(viewLifecycleOwner) {
            if (it.not()) {
                tts.stop()
            }
        }



        viewModel.positionCurrentQuestion.observe(viewLifecycleOwner) { currentPosition ->
            // lấy position hiện tại cập nhật lên màn hình
            val question = viewModel.getQuestionByPosition(currentPosition)
            question?.let {
                viewModel.changeQuestion(it)
            }

            binding.textViewNumberQuestionBottom.text =
                "Câu ${currentPosition.inc()}/${viewModel.questions.value?.size ?: "?"}"

        }
    }


    override fun initEvent() {
        //
        setPanelSlidingListener()
        binding.recyclerViewQuestionOverView.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> binding.layoutSliding.requestDisallowInterceptTouchEvent(
                        true
                    )
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.layoutSliding.requestDisallowInterceptTouchEvent(
                        false
                    )
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    private fun initViewQuestion() {
        binding.containerQuestion.recyclerViewOptions.adapter = listAdapterAnswer

        // hiển thị câu hỏi
        viewModel.question.observe(viewLifecycleOwner) {
            binding.containerQuestion.layoutCardViewDescription.isVisible =
                it.description.isNullOrEmpty().not()
            binding.containerQuestion.textViewQuestion.text = "Mã ${it.id} :\n${it.content}"
            binding.containerQuestion.textViewDescription.displayDescription(it.description)
            // test thì không hiệ
            listAdapterAnswer.submitList(it.answer)
            showImageForQuestionHasImage(it)
            speakQuestion(it)
        }
    }

    private fun showImageForQuestionHasImage(question: Question) {
        if (!question.image.isNullOrEmpty()) {
            binding.containerQuestion.imageViewQuestion.apply {
                val path = Constant.PATH.QUESTION + question.image
                loadImage(path)
                show()
                setOnClickListener {
                    dialog?.viewImage(path)
                }
            }
            binding.containerQuestion.viewItem.hide()
        } else {
            binding.containerQuestion.imageViewQuestion.hide()
            binding.containerQuestion.viewItem.show()
        }
    }

    private fun setPanelSlidingListener() {

        binding.layoutSliding.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                binding.imageViewDropDown.rotation = slideOffset * 180
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {

                when {
                    newState == SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        //binding.textViewNumberQuestionBottom.text = "Mở"
                    }
                    newState == SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        //binding.textViewNumberQuestionBottom.text = "Đóng"
                    }
                }

            }
        })

        binding.layoutSliding.setFadeOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                binding.layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
            }
        })
    }

    private fun onItemCheckedChanged(item: Answer) {

        if (autoNextSettings) {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.nextQuestion()
            }, 300)
        }

    }

    private fun setUpRecyclerViewOverView() {
        binding.recyclerViewQuestionOverView.adapter = listAdapterCirclePositionQuestion

        viewModel.question.observe(viewLifecycleOwner) {

            val list = viewModel.setCurrentQuestionSelected(it)
            listAdapterCirclePositionQuestion.submitList(list)
            listAdapterAnswer.notifyDataSetChanged()
        }
    }

    private fun onClickRecyclerViewOverView(position: Int) {
        viewModel.changePosition(position)
    }


    private fun speakQuestion(question: Question? = null) {
        if (viewModel.voiceSpeech.value == true)
            tts.speech(question?.toText(), utteranceCallback = lístener)
    }


    private fun setSubmenu() {
        binding.imageViewSubmenu.setOnClickListener {
            dialog?.openDialogSetting(
                sharedPreferencesSettings,
                voiceAction = { checked ->
                    viewModel.setVoiceSpeechQuestion(checked)
                    if (checked) {
                        speakQuestion(viewModel.question.value)
                    }
                },
                autoNextAction = { checked ->
                    autoNextSettings = checked
                }
            )

        }
    }


    override fun onStop() {
        tts.stop()
        super.onStop()
    }

    override fun onDestroy() {
        tts.stop()
        super.onDestroy()
    }

}
