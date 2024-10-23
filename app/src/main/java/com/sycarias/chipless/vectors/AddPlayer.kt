package vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val AddPlayer: ImageVector
    get() {
        if (_imageAddPlayer != null) {
            return _imageAddPlayer!!
        }
        _imageAddPlayer = Builder(name = "ImageAddPlayer", defaultWidth = 41.0.dp, defaultHeight =
                41.0.dp, viewportWidth = 41.0f, viewportHeight = 41.0f).apply {
            path(fill = SolidColor(Color(0xFF37373C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(20.5f, 0.0f)
                lineTo(20.5f, 0.0f)
                arcTo(20.5f, 20.5f, 0.0f, false, true, 41.0f, 20.5f)
                lineTo(41.0f, 20.5f)
                arcTo(20.5f, 20.5f, 0.0f, false, true, 20.5f, 41.0f)
                lineTo(20.5f, 41.0f)
                arcTo(20.5f, 20.5f, 0.0f, false, true, 0.0f, 20.5f)
                lineTo(0.0f, 20.5f)
                arcTo(20.5f, 20.5f, 0.0f, false, true, 20.5f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFF4F77)),
                    strokeLineWidth = 0.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(20.5f, 0.25f)
                lineTo(20.5f, 0.25f)
                arcTo(20.25f, 20.25f, 0.0f, false, true, 40.75f, 20.5f)
                lineTo(40.75f, 20.5f)
                arcTo(20.25f, 20.25f, 0.0f, false, true, 20.5f, 40.75f)
                lineTo(20.5f, 40.75f)
                arcTo(20.25f, 20.25f, 0.0f, false, true, 0.25f, 20.5f)
                lineTo(0.25f, 20.5f)
                arcTo(20.25f, 20.25f, 0.0f, false, true, 20.5f, 0.25f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE2E2E2)), stroke = SolidColor(Color(0xFFE2E2E2)),
                    strokeLineWidth = 0.75f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(26.5f, 21.25f)
                horizontalLineTo(21.25f)
                verticalLineTo(26.5f)
                horizontalLineTo(19.75f)
                verticalLineTo(21.25f)
                horizontalLineTo(14.5f)
                verticalLineTo(19.75f)
                horizontalLineTo(19.75f)
                verticalLineTo(14.5f)
                horizontalLineTo(21.25f)
                verticalLineTo(19.75f)
                horizontalLineTo(26.5f)
                verticalLineTo(21.25f)
                close()
            }
        }
        .build()
        return _imageAddPlayer!!
    }

private var _imageAddPlayer: ImageVector? = null
