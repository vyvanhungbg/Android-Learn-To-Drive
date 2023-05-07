package com.suspend.android.learntodrive.data.database.system.dao

import androidx.room.Dao
import androidx.room.Query
import com.suspend.android.learntodrive.model.QuestionEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionDAO {

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS}")
    fun getAll(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE :idQuestion = id LIMIT 1")
    fun getQuestionByID(idQuestion:Int): Single<QuestionEntity>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE question_die == 1")
    fun getAllDieQuestion(): Single<List<QuestionEntity>>


    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type200 > 0 ")
    fun getQuestionByType200(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type450 > 0 ")
    fun getQuestionByType450(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type500 > 0 ")
    fun getQuestionByType500(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type574 > 0 ")
    fun getQuestionByType574(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type600 > 0 ")
    fun getQuestionByType600(): Single<List<QuestionEntity>>


    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type200 > 0 AND question_die == 1")
    fun getQuestionDieByType200(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type450 > 0 AND question_die == 1")
    fun getQuestionDieByType450(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type500 > 0 AND question_die == 1")
    fun getQuestionDieByType500(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type574 > 0 AND question_die == 1")
    fun getQuestionDieByType574(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM ${Constant.DB.TABLES.QUESTIONS} WHERE type600 > 0 AND question_die == 1")
    fun getQuestionDieByType600(): Single<List<QuestionEntity>>

}
