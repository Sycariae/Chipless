package vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Table: ImageVector
    get() {
        if (_imageTable != null) {
            return _imageTable!!
        }
        _imageTable = Builder(name = "ImageTable", defaultWidth = 147.0.dp, defaultHeight =
                246.0.dp, viewportWidth = 147.0f, viewportHeight = 246.0f).apply {
            path(fill = SolidColor(Color(0xFF612F3B)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(72.499f, 0.362f)
                curveTo(112.539f, 0.162f, 145.161f, 32.459f, 145.361f, 72.499f)
                lineTo(145.861f, 172.499f)
                curveTo(146.061f, 212.539f, 113.764f, 245.16f, 73.724f, 245.361f)
                curveTo(33.684f, 245.561f, 1.063f, 213.264f, 0.863f, 173.224f)
                lineTo(0.363f, 73.224f)
                curveTo(0.162f, 33.184f, 32.459f, 0.563f, 72.499f, 0.362f)
                close()
            }
            path(fill = SolidColor(Color(0xFF212124)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(72.512f, 2.862f)
                curveTo(111.171f, 2.669f, 142.667f, 33.852f, 142.861f, 72.512f)
                lineTo(143.361f, 172.512f)
                curveTo(143.554f, 211.171f, 112.371f, 242.667f, 73.712f, 242.861f)
                curveTo(35.052f, 243.054f, 3.556f, 211.871f, 3.362f, 173.212f)
                lineTo(2.862f, 73.212f)
                curveTo(2.669f, 34.552f, 33.852f, 3.056f, 72.512f, 2.862f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFFF4F77)),
                    strokeLineWidth = 0.75f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(72.513f, 3.237f)
                curveTo(110.966f, 3.045f, 142.293f, 34.061f, 142.486f, 72.513f)
                lineTo(142.986f, 172.513f)
                curveTo(143.178f, 210.966f, 112.162f, 242.293f, 73.71f, 242.486f)
                curveTo(35.257f, 242.678f, 3.93f, 211.662f, 3.737f, 173.21f)
                lineTo(3.237f, 73.21f)
                curveTo(3.045f, 34.757f, 34.061f, 3.43f, 72.513f, 3.237f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2C2C30)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(72.624f, 25.362f)
                curveTo(98.857f, 25.231f, 120.23f, 46.391f, 120.361f, 72.624f)
                lineTo(120.861f, 172.624f)
                curveTo(120.992f, 198.857f, 99.832f, 220.23f, 73.599f, 220.361f)
                curveTo(47.366f, 220.492f, 25.993f, 199.332f, 25.862f, 173.099f)
                lineTo(25.362f, 73.099f)
                curveTo(25.231f, 46.866f, 46.391f, 25.493f, 72.624f, 25.362f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFB1B1B1)),
                    strokeAlpha = 0.3f, strokeLineWidth = 1.0f, strokeLineCap = Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(120.861f, 72.622f)
                curveTo(120.728f, 46.112f, 99.131f, 24.73f, 72.622f, 24.862f)
                curveTo(46.112f, 24.995f, 24.73f, 46.592f, 24.862f, 73.102f)
                lineTo(25.362f, 173.102f)
                curveTo(25.495f, 199.611f, 47.092f, 220.994f, 73.602f, 220.861f)
                curveTo(100.111f, 220.728f, 121.494f, 199.131f, 121.361f, 172.622f)
                lineTo(120.861f, 72.622f)
                close()
            }
        }
        .build()
        return _imageTable!!
    }

private var _imageTable: ImageVector? = null
