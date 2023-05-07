package com.suspend.android.learntodrive.di

import android.content.Context
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.suspend.android.learntodrive.utils.extension.logError
import org.koin.dsl.module

private const val TAG = "YoutubeExtractModule"


fun provideYouTubeExtractor(
    context: Context,
    onSuccess: (streamURL: String) -> Unit,
    onFailed: () -> Unit,
): YouTubeExtractor {
    return object : YouTubeExtractor(context) {
        override fun onExtractionComplete(
            sparseArray: SparseArray<YtFile>?,
            videoMeta: VideoMeta?
        ) {
            try {
                if (sparseArray == null) {
                    logError(TAG, "Error when get video all link null")
                    throw Exception("Error when get video all link null")
                } else {
                    val ytFile = sparseArray.get(sparseArray.keyAt(0))

                    val streamURL = ytFile.url
                    onSuccess(streamURL)
                    logError(TAG, "Sucessfully get link video ${streamURL}")
                }
            } catch (e: Exception) {
                logError(TAG, "Error when get video all link null ${e.message}")
                onFailed.invoke()
            }
            // Get the stream URL of the highest quality format available

        }
    }
}