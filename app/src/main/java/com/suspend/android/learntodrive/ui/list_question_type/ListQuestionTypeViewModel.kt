package com.suspend.android.learntodrive.ui.list_question_type

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.question.IQuestionRepository
import com.suspend.android.learntodrive.data.repository.question_type.IQuestionTypeRepository
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.model.QuestionTypeEntity
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseType
import com.suspend.android.learntodrive.utils.extension.logError
import io.reactivex.rxjava3.core.Single

private const val TAG = "ListQuestionTypeViewMod"

class ListQuestionTypeViewModel(
    private val questionRepository: IQuestionRepository,
    private val questionTypeRepository: IQuestionTypeRepository,
    private val sharedPreferencesSettings: SharedPreferences
) : BaseViewModel() {

    private val _questions = MutableLiveData<List<QuestionEntity>>()
    val questions: LiveData<List<QuestionEntity>>
        get() = _questions

    private val _questionsType = MutableLiveData<List<QuestionTypeEntity>>()
    val questionsType: LiveData<List<QuestionTypeEntity>>
        get() = _questionsType



    fun getQuestionsByType(context: Context?) {
        registerDisposable(
            executeTask(
                task = {
                    Single.zip(
                        questionRepository.getQuestionByLicense(sharedPreferencesSettings.getCurrentLicenseType()),
                        questionTypeRepository.getAll()
                    ) { _questions, _questionType ->
                        return@zip Pair(_questions, _questionType)
                    }
                },
                onSuccess = {
                    val questions = it.first
                    val questionType = it.second
                    val newQuestionType = questionType.map { type ->
                        // tìm ra các câu hỏi, câu hỏi điểm liệt  của mỗi loại
                        val questionsWithType =
                            questions.filter { question -> question.questionType == type.id }
                        val questionsDieWithType =
                            questionsWithType.filter { question -> question.isQuestionDie() }
                        if(questionsWithType.isEmpty()){
                            return@map null;
                        }else
                        if (questionsDieWithType.isEmpty()) {
                            return@map type.copy(
                                description = context?.getString(
                                    R.string.description_question_type_no_question_die,
                                    questionsWithType.size.toString()
                                )
                            )
                        } else {
                            return@map type.copy(
                                description = context?.getString(R.string.description_question_type, *arrayOf(questionsWithType.size.toString(), questionsDieWithType.size.toString()))
                            )
                        }
                    }
                    _questionsType.value = newQuestionType.filterNotNull().toList()
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }

   /* fun getQuestionsType() {
        registerDisposable(
            executeTask(
                task = { questionTypeRepository.getAll() },
                onSuccess = {
                    _questionsType.value = it
                },
                onError = {
                    logError(TAG, it.toString())
                }
            )
        )
    }*/


}