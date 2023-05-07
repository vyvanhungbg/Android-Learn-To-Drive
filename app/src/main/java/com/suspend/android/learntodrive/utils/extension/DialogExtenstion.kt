package com.suspend.android.learntodrive.utils.extension

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.DlInfoInTestBinding
import com.suspend.android.learntodrive.databinding.DlQuestionYesNoBinding
import com.suspend.android.learntodrive.databinding.DlSettingInTestBinding
import com.suspend.android.learntodrive.databinding.DlShowImageDetailBinding
import com.suspend.android.learntodrive.databinding.DlViewQuestionBinding
import com.suspend.android.learntodrive.model.Question
import com.suspend.android.learntodrive.ui.question.ListAdapterAnswer
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.constant.Constant


fun Dialog.start(stopFlag: Boolean = false) {
    val marginY = -170
    this.let {
        it.setContentView(R.layout.dl_progress_bar)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
        it.setCancelable(stopFlag)
        it.show()
    }
}

fun Dialog.viewImage(pathImage: String) {
    val marginY = -170
    var currentRotate = Constant.DEFAULT.IMAGE_HORIZONTAL
    this.let {
        val binding = DlShowImageDetailBinding.inflate(layoutInflater)
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
        binding.imageViewItem.loadImageNoCache(pathImage)
        binding.imageViewExit.setOnClickListener {
            dismiss()
        }
        binding.imageViewRotate.setOnClickListener {
            currentRotate =
                if (currentRotate == Constant.DEFAULT.IMAGE_HORIZONTAL)
                    Constant.DEFAULT.IMAGE_VERTICAL
                else Constant.DEFAULT.IMAGE_HORIZONTAL
            binding.imageViewItem.setRotationTo(currentRotate)
        }
        it.setCancelable(true)
        it.show()
    }
}


fun Dialog.openDialogQuestion(title: String, content: String, confirmAction: () -> Unit) {
    val marginY = -170
    this.let {
        val binding = DlQuestionYesNoBinding.inflate(layoutInflater)
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            confirmAction()
            dismiss()
        }
        binding.textViewTitle.text = title
        binding.textViewContent.text = content
        it.setCancelable(true)
        it.show()
    }
}

fun Dialog.getView(question: Question): View {
    val marginY = -170
    val binding = DlViewQuestionBinding.inflate(layoutInflater)
    val listAdapterAnswer by lazy {
        ListAdapterAnswer(
            onClick = { },
            showAnswerFailed = true,
            alwaysShowCorrectAnswer = true
        )
    }

    this.let {
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
    }
    binding.containerQuestion.recyclerViewOptions.adapter =
        listAdapterAnswer

    fun showImageForQuestionHasImage(question: Question) {
        if (!question.image.isNullOrEmpty()) {
            binding.containerQuestion.imageViewQuestion.apply {
                val path = Constant.PATH.QUESTION + question.image
                loadImage(path)
                show()
                logError(message = "show imAGE")
            }
            binding.containerQuestion.viewItem.hide()
        } else {
            binding.containerQuestion.imageViewQuestion.hide()
            binding.containerQuestion.viewItem.show()
            logError(message = "hide imAGE")
        }
    }
    // hiển thị câu hỏi
    question.let { _question ->
        binding.containerQuestion.layoutCardViewDescription.isVisible =
            _question.description.isNullOrEmpty().not()
        binding.containerQuestion.textViewQuestion.text =
            "Mã ${_question.id} :\n${_question.content}"
        binding.containerQuestion.textViewDescription
            .displayDescription(_question.description)
        // test thì không hiệ
        listAdapterAnswer.submitList(_question.answer)
        showImageForQuestionHasImage(_question)

    }
    show()
    return binding.root
}

fun Dialog.openDialogSetting(
    sharedPreferencesSettings: SharedPreferences,
    voiceAction: (Boolean) -> Unit,
    autoNextAction: (Boolean) -> Unit
) {
    val marginY = -170
    this.let {
        val binding = DlSettingInTestBinding.inflate(layoutInflater)
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        val settingsVoiceChecked = sharedPreferencesSettings.getVoiceSpeechSettings()
        binding.switchButtonVoice.isChecked = settingsVoiceChecked
        binding.switchButtonVoice.setOnClickListener {
            val currentChecked = binding.switchButtonVoice.isChecked
            voiceAction(currentChecked)
            sharedPreferencesSettings.setVoiceSpeechSettings(currentChecked)
        }
        val settingsAutoNextChecked = sharedPreferencesSettings.getAutoNextSpeechSettings()
        binding.switchButtonAutoNext.isChecked = settingsAutoNextChecked
        binding.switchButtonAutoNext.setOnClickListener {
            val currentChecked = binding.switchButtonAutoNext.isChecked
            autoNextAction(currentChecked)
            sharedPreferencesSettings.setAutoNextSpeechSettings(currentChecked)
        }

        it.setCancelable(true)
        it.show()
    }
}

fun Dialog.openDialogInfoTest(
    type: TYPE,
    nameExam: String? = null,
    timeExam: String? = null,
    totalExam: String? = null,
    minimumExam: String? = null,
    dieQuestion: String? = null
) {
    val marginY = -170
    this.let {
        val binding = DlInfoInTestBinding.inflate(layoutInflater)
        it.setContentView(binding.root)
        it.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                y = marginY
                gravity = Gravity.CENTER
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        nameExam?.let {
            binding.textViewTitleNameExam.text = context.getString(if(type == TYPE.TEST) R.string.title_name_test else R.string.title_name_lesson, it)
        }
        timeExam?.let {
            binding.textViewTitleTime.text = context.getString(R.string.title_time_test, it)
        }
        totalExam?.let {
            binding.textViewTitleTotalQuestion.text =
                context.getString(R.string.title_total_question, it)
        }
        minimumExam?.let {
            if(type == TYPE.TEST){
                binding.textViewTitleMinimumQuestion.text =
                    context.getString(R.string.title_minimum_question, it)
            }else{
                binding.textViewTitleMinimumQuestion.isVisible = false
            }
        }
        dieQuestion?.let {
            binding.textViewTitleDieQuestion.text =
                context.getString(R.string.title_die_question, it)
        }
        it.setCancelable(true)
        it.show()
    }
}
