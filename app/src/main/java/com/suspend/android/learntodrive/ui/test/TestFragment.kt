package com.suspend.android.learntodrive.ui.test

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.suspend.android.learntodrive.base.BaseFragment
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.powerMenu
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentTestBinding
import com.suspend.android.learntodrive.di.SHARED_PREFERENCES_TYPE
import com.suspend.android.learntodrive.model.Answer
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.model.toText
import com.suspend.android.learntodrive.ui.question.ListAdapterAnswer
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.addNewList
import com.suspend.android.learntodrive.utils.extension.displayDescription
import com.suspend.android.learntodrive.utils.extension.drawable
import com.suspend.android.learntodrive.utils.extension.getAutoNextSpeechSettings
import com.suspend.android.learntodrive.utils.extension.hide
import com.suspend.android.learntodrive.utils.extension.loadImage
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.suspend.android.learntodrive.utils.extension.openDialogInfoTest
import com.suspend.android.learntodrive.utils.extension.openDialogQuestion
import com.suspend.android.learntodrive.utils.extension.openDialogSetting
import com.suspend.android.learntodrive.utils.extension.show
import com.suspend.android.learntodrive.utils.extension.speech
import com.suspend.android.learntodrive.utils.extension.toMinutesSecond
import com.suspend.android.learntodrive.utils.extension.viewImage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


private const val TAG = "TestFragment"

class TestFragment : BaseFragment<FragmentTestBinding>(FragmentTestBinding::inflate) {

    override val viewModel by viewModel<TestViewModel>()
    private val moreMenu by powerMenu<MoreMenuFactory>()
    private val safeArgs: TestFragmentArgs by navArgs()

    private val tts: TextToSpeech by inject()

    private val sharedPreferencesSettings: SharedPreferences by inject(
        qualifier = named(
            SHARED_PREFERENCES_TYPE.SETTINGS
        )
    )

    private var modeTest = false

    private var autoNextSettings = false

    private val listAdapterAnswer by lazy {
        ListAdapterAnswer(
            ::onItemCheckedChanged,
            modeTest.not(),
        )
    }

    private val listAdapterCirclePositionQuestion by lazy {
        ListAdapterCirclePositionQuestion(
            ::onClickRecyclerViewOverView,
            modeTest.not()
        )
    }

    private val list = mutableListOf<Answer>()


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


    private val callbackHandler = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            actionBackTest()
        }
    }

    override fun initData() {
        modeTest = safeArgs.bundleType == TYPE.TEST
        context?.let {
            dialog = Dialog(it)
        }
        autoNextSettings = sharedPreferencesSettings.getAutoNextSpeechSettings()
    }


    override fun initView() {
        if (safeArgs.bundleTest != null) {
            viewModel.intiTest(safeArgs.bundleTest!!)
            //binding.textViewTitleExam.text = safeArgs.bundleTest?.name
        } else {
            findNavController().popBackStack()
        }
        binding.containerQuestion.layoutCardViewDescription.isVisible = modeTest.not()
        setPanelSlidingListener()
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
                actionBackTest()
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

            /* binding.textViewNumberQuestion.text =
                 "${currentPosition.inc()}/${viewModel.test.value?.totalQuestion.toString()}"*/

            binding.textViewNumberQuestionBottom.text =
                "Câu ${currentPosition.inc()}/${viewModel.test.value?.questions?.size ?: "?"}"
            Log.e(TAG, viewModel.test.value.toString())
            Log.e(TAG, question.toString())
        }

        viewModel.countDown.observe(viewLifecycleOwner) {
            binding.textViewTimeRemaining.text = it.toMinutesSecond()
        }

        viewModel.finishCountDown.observe(viewLifecycleOwner) {
            binding.textViewTimeRemaining.text = "Hết giờ"
            finishTest()
        }


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callbackHandler)
    }

    private fun actionBackTest() {
        dialog?.openDialogQuestion(
            getString(R.string.text_confirm_exit),
            getString(R.string.text_message_exit_to_destroy_test)
        ) {
            findNavController().popBackStack()
        }
    }

    private fun finishTest() {
        val result = viewModel.calcCoreOfTest(safeArgs.bundleTest)
        val action = TestFragmentDirections.actionNavigationTestToNavigationResult(bundleResult = result, bundleType = if(modeTest) TYPE.TEST else TYPE.LEARN)
        findNavController().navigateSafe(action)
    }

    override fun initEvent() {
        //
        binding.recyclerViewQuestionOverView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> binding.layoutSliding.requestDisallowInterceptTouchEvent(true)
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> binding.layoutSliding.requestDisallowInterceptTouchEvent(false)
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
            binding.containerQuestion.textViewQuestion.text = "Mã ${it.id} :\n${it.content}"
            binding.containerQuestion.textViewDescription.displayDescription(it.description)
            // test thì không hiện
            val isShowDescription =
                modeTest.not() && it.answerSelected != Question.DEFAULT_ANSWER_SELECTED
            binding.containerQuestion.layoutCardViewDescription.isVisible = isShowDescription
            list.addNewList(it.answer)
            listAdapterAnswer.submitList(list)
            listAdapterAnswer.notifyDataSetChanged()
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
        val isShowDescription =
            modeTest.not() && viewModel.question.value?.description.isNullOrEmpty().not()
        binding.containerQuestion.layoutCardViewDescription.isVisible = isShowDescription

        val itemWithNewState = item.copy(checked = item.checked.not())
        // set state câu hỏi chọn và unselect các câu hỏi khác
        val newListQuestion = list.map {
            if (it.position == item.position)
                itemWithNewState
            else it.copy(checked = false)
        }

        list.addNewList(newListQuestion)
        listAdapterAnswer.submitList(list)
        listAdapterAnswer.notifyDataSetChanged()

        // lưu lại vị trí chọn câu hỏi
        viewModel.setSelectedAnswer(itemWithNewState)

        //update list in bottom
        viewModel.test.value?.let {
            listAdapterCirclePositionQuestion.submitList(it.questions)
            listAdapterAnswer.notifyDataSetChanged()
        }

        //disable deselected answer
        binding.containerQuestion.recyclerViewOptions.isClickable = false

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
            moreMenu?.showAsAnchorRightBottom(it)
            moreMenu?.setOnMenuItemClickListener(object : OnMenuItemClickListener<PowerMenuItem> {
                override fun onItemClick(position: Int, item: PowerMenuItem?) {
                    when (position) {
                        0 -> {
                             finishTest()
                           /* gen(
                                requireActivity(), TestEntity(
                                    1, "12", 3, 3, 3, 3, "1", true,
                                    mutableListOf(), Date()
                                ), binding.root
                            )*/
                        }
                        1 -> {
                            actionBackTest()
                        }
                        2 -> {
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
                        3 -> {
                            val test = safeArgs.bundleTest
                            test?.let {
                                dialog?.openDialogInfoTest(
                                    safeArgs.bundleType,
                                    it.name,
                                    it.time.toString(),
                                    it.questions.size.toString(),
                                    it.minimumQuestion.toString(),
                                    it.questions.count { it.questionDie }.toString()
                                )
                            }
                        }
                    }
                }

            })
            if (moreMenu?.isShowing == true) {
                binding.imageViewSubmenu.setImageDrawable(context?.drawable(R.drawable.ic_menu_close_24dp))
            }
        }
        moreMenu?.setOnDismissedListener {

            logError(message = "Dimess")
            binding.imageViewSubmenu.setImageDrawable(context?.drawable(R.drawable.ic_baseline_menu_24))
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

enum class TYPE {
    TEST,
    LEARN
}
