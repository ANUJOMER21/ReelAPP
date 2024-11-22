/*
package com.Vginfotech.reelapp*/

package com.Vginfotech.reelapp.page

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Vginfotech.reelapp.ui.theme.ReelAPPTheme


private const val AnimationDurationMillis = 600

enum class SelectionMode(val index: Int) {
    Single(0),
    Multiple(1);

    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        fun fromIndex(index: Int) = entries.firstOrNull { it.index == index } ?: Single
    }
}

@Stable
interface ChipSelectorState {
    val chips: List<String>
    val selectedChips: List<String>

    fun onChipClick(chip: String)
    fun isSelected(chip: String): Boolean
}

class ChipSelectorStateImpl(
    override val chips: List<String>,
    selectedChips: List<String> = emptyList(),
    val mode: SelectionMode = SelectionMode.Single,
) : ChipSelectorState {
    override var selectedChips by mutableStateOf(selectedChips)

    override fun onChipClick(chip: String) {
        if (mode == SelectionMode.Single) {
            if (!selectedChips.contains(chip)) {
                selectedChips = listOf(chip)
            }
        } else {
            selectedChips = if (selectedChips.contains(chip)) {
                selectedChips - chip
            } else {
                selectedChips + chip
            }
        }
    }

    override fun isSelected(chip: String): Boolean = selectedChips.contains(chip)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChipSelectorStateImpl

        if (chips != other.chips) return false
        if (mode != other.mode) return false
        if (selectedChips != other.selectedChips) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chips.hashCode()
        result = 31 * result + mode.hashCode()
        result = 31 * result + selectedChips.hashCode()
        return result
    }

    companion object {
        val saver = Saver<ChipSelectorStateImpl, List<*>>(
            save = { state ->
                buildList {
                    add(state.chips.size)
                    addAll(state.chips)
                    add(state.selectedChips.size)
                    addAll(state.selectedChips)
                    add(state.mode.index)
                }
            },
            restore = { items ->
                var index = 0
                val chipsSize = items[index++] as Int
                val chips = List(chipsSize) {
                    items[index++] as String
                }
                val selectedSize = items[index++] as Int
                val selectedChips = List(selectedSize) {
                    items[index++] as String
                }
                val mode = SelectionMode.fromIndex(items[index] as Int)
                ChipSelectorStateImpl(
                    chips = chips,
                    selectedChips = selectedChips,
                    mode = mode,
                )
            }
        )
    }
}

@Composable
fun rememberChipSelectorState(
    chips: List<String>,
    selectedChips: List<String> = emptyList(),
    mode: SelectionMode = SelectionMode.Single,
): ChipSelectorState {
    if (chips.isEmpty()) error("No chips provided")
    if (mode == SelectionMode.Single && selectedChips.size > 1) {
        error("Single choice can only have 1 pre-selected chip")
    }

    return rememberSaveable(
        saver = ChipSelectorStateImpl.saver
    ) {
        ChipSelectorStateImpl(
            chips,
            selectedChips,
            mode,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsSelector(
    state: ChipSelectorState,
    modifier: Modifier = Modifier,
    selectedTextColor: Color = Color.White,
    unselectedTextColor: Color = Color.White,
    selectedBackgroundColor: Color = Color(0xFF5800FF),
    unselectedBackgroundColor: Color =Color(0xFF000000),
    borderColor: Color =Color(0xFF304FFE),
    borderWidth: Dp = 2.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        state.chips.forEach { chip ->
            Chip(
                label = chip,
                isSelected = state.isSelected(chip),
                onClick = { state.onChipClick(chip) },
                selectedTextColor = selectedTextColor,
                unselectedTextColor = unselectedTextColor,
                selectedBackgroundColor = selectedBackgroundColor,
                unselectedBackgroundColor = unselectedBackgroundColor,
                borderColor = borderColor,
                borderWidth = borderWidth,
            )
        }
    }
}

@Composable
fun Chip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    unselectedTextColor: Color = Color.Black,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    unselectedBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    borderWidth: Dp = 2.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val path = remember { Path() }
    val borderWidthPx = with(LocalDensity.current) { borderWidth.toPx() }
    val pathMeasure = remember { PathMeasure() }
    val pathSegment = remember { Path() }
    val transition = updateTransition(targetState = isSelected, label = "transition")
    val pathFraction by transition.animateFloat(
        transitionSpec = { tween(durationMillis = AnimationDurationMillis) },
        label = "pathSegment"
    ) { selected ->
        if (selected) 1f else 0f
    }
    val backgroundColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = AnimationDurationMillis) },
        label = "backgroundColor"
    ) { selected ->
        if (selected) selectedBackgroundColor else unselectedBackgroundColor
    }
    val textColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = AnimationDurationMillis) },
        label = "textColor",
    ) { selected ->
        if (selected) selectedTextColor else unselectedTextColor
    }
    val textAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = AnimationDurationMillis) },
        label = "textAlpha"
    ) { selected ->
        if (selected) 1f else 1f
    }
    Box(
        modifier = modifier
            .drawWithCache {
                computePath(path, size, borderWidthPx)
                pathMeasure.setPath(path, false)
                pathSegment.reset()
                pathMeasure.getSegment(0f, pathMeasure.length * pathFraction, pathSegment)
                onDrawBehind {
                    drawPath(
                        path = path,
                        color = backgroundColor,
                        style = Fill,
                    )
                    drawPath(
                        path = pathSegment,
                        color = borderColor,
                        style = Stroke(width = borderWidthPx),
                    )
                }
            }
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .graphicsLayer { alpha = textAlpha }

        )
    }
}

private fun computePath(
    path: Path,
    size: Size,
    borderWidth: Float,
) {
    val cornerRadius = size.height / 2f
    with(path) {
        moveTo(borderWidth + size.width / 2f, borderWidth)
        lineTo(size.width - borderWidth - cornerRadius, borderWidth)
        quadraticBezierTo(
            size.width - borderWidth,
            borderWidth,
            size.width - borderWidth,
            borderWidth + cornerRadius,
        )
        quadraticBezierTo(
            size.width - borderWidth,
            size.height - borderWidth,
            size.width - borderWidth - cornerRadius,
            size.height - borderWidth,
        )
        lineTo(borderWidth + cornerRadius, size.height - borderWidth)
        quadraticBezierTo(
            borderWidth,
            size.height - borderWidth,
            borderWidth,
            size.height - cornerRadius - borderWidth,
        )
        quadraticBezierTo(
            borderWidth,
            borderWidth,
            borderWidth + cornerRadius,
            borderWidth,
        )
        close()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewChip() {
    ReelAPPTheme  {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            var isSelected by remember {
                mutableStateOf(false)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(all = 24.dp)
            ) {
                Chip(
                    label = "Avocado",
                    isSelected = isSelected,
                    onClick = { isSelected = !isSelected },
                )
                Chip(
                    label = "Strawberry",
                    isSelected = true,
                    onClick = { isSelected = !isSelected },
                )
            }
        }
    }
}

@Preview(widthDp = 720)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 720)
@Composable
private fun PreviewChipSelector() {
    ReelAPPTheme  {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val chips = remember {
                listOf("Banana", "Blueberry", "Strawberry", "Pineapple", "Avocado")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(all = 0.dp)
            ) {


                Text(text = "Multiple selection")
                val state2 = rememberChipSelectorState(chips = chips, mode = SelectionMode.Multiple)
                ChipsSelector(
                    state = state2,

                )
            }
        }
    }
}

