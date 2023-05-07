package com.suspend.android.learntodrive.utils.extension

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.core.os.bundleOf
import com.suspend.android.learntodrive.utils.constant.Constant
import java.io.File
import java.util.Locale

private const val TAG = "TextToSpeechExtension"
fun TextToSpeech.speech(
    text: String?,
    locale: Locale? = null,
    utteranceCallback: UtteranceProgressListener? = null,
) {
    locale?.let {
        this.language = it
    }
    utteranceCallback?.let {
        this.setOnUtteranceProgressListener(it)
    }
    if (this.isSpeaking) {
        this.stop()
        logError(message = "stop")
    }
    this.speak(
        text,
        TextToSpeech.QUEUE_FLUSH,
        bundleOf(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID to ""),
        TAG
    )
}

fun TextToSpeech.writeFileAudio(
    context: Context,
    text: String,
    fileName: String,
    utteranceCallback: UtteranceProgressListener? = null,
): String {
    val fileNameFormat = fileName.plus(Constant.TEXTTOSPEECH.DEFAULT_EXTENSION)
    val pathFile =
        File(context.getExternalFilesDir(Constant.TEXTTOSPEECH.DEFAULT_FOLDER), fileNameFormat)
    this.synthesizeToFile(text, Bundle(), pathFile, fileName)
    utteranceCallback?.let {
        this.setOnUtteranceProgressListener(it)
    }
    return pathFile.path
}
