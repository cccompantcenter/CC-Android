package com.cc.commandcenter.ink

/**
 * Reusable abstraction for future S Pen handwriting support.
 *
 * Responsibilities:
 * - Ink capture: receive raw pointer or stroke data from the UI.
 * - Stroke storage: retain handwritten strokes independently from the text layer.
 * - Handwriting recognition: transform captured ink into text when a provider is available.
 * - Text conversion: expose a simple text representation for the current save flow.
 *
 * This abstraction is intentionally provider-agnostic so Samsung or any future AI engine
 * can be integrated without changing the rest of the app.
 */
interface CCInkCanvas {
    fun captureStroke(stroke: InkStroke)
    fun clearStrokes()
    fun strokes(): List<InkStroke>
    fun recognizedText(): String
    fun convertToText(): String
}

data class InkStroke(
    val id: Long,
    val points: List<InkPoint>
)

data class InkPoint(
    val x: Float,
    val y: Float
)

/**
 * Minimal in-memory implementation used as a visible drawing surface until a real handwriting layer exists.
 */
class DefaultCCInkCanvas : CCInkCanvas {
    private val storedStrokes = mutableListOf<InkStroke>()

    override fun captureStroke(stroke: InkStroke) {
        storedStrokes.add(stroke)
    }

    override fun clearStrokes() {
        storedStrokes.clear()
    }

    override fun strokes(): List<InkStroke> = storedStrokes.toList()

    override fun recognizedText(): String = ""

    override fun convertToText(): String = ""
}
