package com.cc.commandcenter.components

import android.graphics.Typeface
import android.text.InputType
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.doAfterTextChanged
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun SpenInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = false,
    minLines: Int = if (singleLine) 1 else 3,
    minHeight: Dp = 0.dp,
    autofocus: Boolean = false,
    onAutofocusApplied: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF1A180F)
    val borderColor = CcText.copy(alpha = 0.65f)
    var focusApplied by remember(autofocus) { mutableStateOf(false) }

    Box(
        modifier = modifier
            .heightIn(min = minHeight)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                EditText(context).apply {
                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    setTextColor(CcText.toArgb())
                    setHintTextColor(CcMuted.toArgb())
                    textSize = 18f
                    typeface = Typeface.SANS_SERIF
                    this.minLines = minLines
                    setLines(minLines)
                    isSingleLine = singleLine
                    setHorizontallyScrolling(singleLine)
                    isVerticalScrollBarEnabled = true
                    overScrollMode = android.view.View.OVER_SCROLL_IF_CONTENT_SCROLLS
                    inputType = if (singleLine) {
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    } else {
                        InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                            InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    }
                    imeOptions = android.view.inputmethod.EditorInfo.IME_FLAG_NO_EXTRACT_UI
                    hint = placeholder
                    doAfterTextChanged {
                        onValueChange(it?.toString().orEmpty())
                    }
                }
            },
            update = { editText ->
                if (editText.text?.toString() != value) {
                    editText.setText(value)
                    editText.setSelection(editText.text?.length ?: 0)
                }
                if (autofocus && !focusApplied) {
                    editText.requestFocus()
                    editText.post {
                        editText.setSelection(editText.text?.length ?: 0)
                    }
                    focusApplied = true
                    onAutofocusApplied()
                }
            }
        )
    }
}
