package com.suspend.android.learntodrive.utils.constant

object Constant {

    const val PROJECT_NAME = "com.suspend.android.learntodrive."

    object DELAY {
        const val INPUT_TEXT = 600L
    }

    object DEFAULT {
        const val FIRST_ACTION = 1L
        const val IMAGE_HORIZONTAL = 0F
        const val IMAGE_VERTICAL = 90F
        const val BUNDLE_INT_ERROR = -1
    }

    object DB {
        const val VERSION = 1
        const val NAME = "LICENSE_DRIVE"
        const val PATH = "databases/database.db"

        object TABLES {
            const val QUESTIONS = "QUESTIONS"
            const val QUESTION_TYPE = "QUESTION_TYPE"
            const val EXAM_SET = "EXAM_SET"
            const val LICENSE = "LICENSE"
            const val SIGNS = "SIGNS"
            const val SIGN_TYPE = "SIGN_TYPE"
            const val TIPS = "TIPS"
            const val QUESTION_SIMULATION = "QUESTION_SIMULATION"
            const val QUESTION_SIMULATION_TYPE = "QUESTION_SIMULATION_TYPE"

        }
    }

    object DB_USER{
        const val VERSION = 1
        const val NAME = "DATA"

        object TABLES {
            const val TEST = "TEST"
            const val ALARM = "ALARM"
        }
    }

    object ALARM{
        const val INTENT_FILTER = "com.suspend.android.learntodrive.ALARM_REDMINE"
        const val BUNDLE_ALARM_ENTITY_ID = "com.suspend.android.learntodrive.BUNDLE_ALARM_ENTITY_ID"
    }

    object PATH {
        private const val BASE = "file:///android_asset/"
        const val QUESTION = BASE+"images/"
        const val IMAGES = BASE+"images/"
        const val EXTENSION_IMAGE = ".png"
        const val TRAFFIC_SIGN = BASE+"signs/sign"
        const val TIPS_600 = BASE+"html/tips600.html"
        const val TIPS_A1 = BASE+"html/howtoexam_a1.html"
        const val TIPS_A2 = BASE+"html/howtoexam_a2.html"
        const val TIPS_A3 = BASE+"html/howtoexam_a3.html"
        const val TIPS_A4 = BASE+"html/howtoexam_a4.html"
        const val TIPS_B1 = BASE+"html/howtoexam_b1.html"
        const val TIPS_B2 = BASE+"html/howtoexam_b2.html"
        const val TIPS_C = BASE+"html/howtoexam_c.html"
        const val TIPS_DEF = BASE+"html/howtoexam_def.html"
        const val TIPS_EXAM_DRIVE = BASE+"html/practice_exam.html"
    }

    object TIME {
        const val SECOND_TO_MILLI = 1000L
    }

    object SHAREDPREF {
        const val ROOT = PROJECT_NAME.plus("SHARED_FILE")

        object SETTINGS {
            const val VOICE = PROJECT_NAME + "VOICE"
            const val AUTO_NEXT = PROJECT_NAME + "AUTO_NEXT"
            const val LICENSE_TYPE = PROJECT_NAME + "LICENSE_TYPE"
            const val RESTORE_SETTING = PROJECT_NAME + "RESTORE_SETTING"
            const val DARK_MODE = PROJECT_NAME + "DARK_MODE"
            const val ALARM = PROJECT_NAME + "ALARM"
        }
    }

    object TEXTTOSPEECH {
        const val DEFAULT_LOCALE = "vi"
        const val DEFAULT_EXTENSION = ".wav"
        const val DEFAULT_FOLDER = "audio"
    }
}