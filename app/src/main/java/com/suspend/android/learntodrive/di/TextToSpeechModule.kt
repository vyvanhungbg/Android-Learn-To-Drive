package com.suspend.android.learntodrive.di

import android.app.Application
import android.speech.tts.TextToSpeech
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.speech
import org.koin.dsl.module
import java.util.Locale

private const val TAG = "TextToSpeechModule"
val textToSpeechModule = module {
    single { provideTextToSpeech(get()) }
}

private fun provideTextToSpeech(androidApplication: Application): TextToSpeech {
    val listener = TextToSpeech.OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) {
            logError(TAG, "init TTS successfully ! ")
        } else {
            logError(TAG, "init TTS failed ! ")
        }
    }
    val language = Locale.forLanguageTag(Constant.TEXTTOSPEECH.DEFAULT_LOCALE)
    val textToSpeech = TextToSpeech(androidApplication, listener)
    if (textToSpeech.isLanguageAvailable(language) == TextToSpeech.LANG_AVAILABLE) {
        textToSpeech.language = language
    }
//com.google.android.tts
    return textToSpeech
}
