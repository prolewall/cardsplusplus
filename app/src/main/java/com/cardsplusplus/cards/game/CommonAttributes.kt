package com.cardsplusplus.cards.game

import android.text.TextPaint
import androidx.core.content.res.ResourcesCompat
import com.cardsplusplus.cards.App
import com.cardsplusplus.cards.R

val cardFont = TextPaint().apply{
    typeface = ResourcesCompat.getFont(App.context, R.font.cards_font)
}