package prism.re.gan.prism.ui.directions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import prism.re.gan.prism.R;

/**
 * Created by rmbitiru on 2/23/15.
 */
public class CompassView extends View {

    private int rotationDegree = 0 ;
    private final String INCORRECT_DEGREE_VALUE = "Incorrect rotation degree value entered" ;

    public CompassView (Context context) {
        super(context);
        initCompassView() ;
    }

    public CompassView (Context context, AttributeSet attributeSet,
                        int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        initCompassView() ;
    }

    public CompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initCompassView() ;
    }

    protected void initCompassView () {
        setFocusable(true);
    }

    public void setRotationDegree(int rotationDegree) {
        if (rotationDegree >=0 && rotationDegree <= 360) {
            this.rotationDegree = rotationDegree;
            invalidate();
        } else
            throw new IllegalArgumentException(INCORRECT_DEGREE_VALUE) ;
    }

    public int getRotationDegree() {
        return rotationDegree;
    }

    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {
        int measuredHeight = measure(hMeasureSpec) ;
        int measuredWidth = measure(wMeasureSpec) ;

        int smallerDimension = Math.min(measuredWidth, measuredHeight) ;
        setMeasuredDimension(smallerDimension, smallerDimension);
    }

    private int measure (int measureSpec) {
        int result ;
        int specMode = MeasureSpec.getMode(measureSpec) ;
        int specSize = MeasureSpec.getSize(measureSpec) ;

        if (specMode == MeasureSpec.UNSPECIFIED)
            result = 200 ;
        else
            result = specSize ;

        return result ;
    }

    public void onDraw(Canvas canvas) {

        int height = getMeasuredHeight() ;
        int width = getMeasuredWidth() ;
        final int OFFSET = 26 ;
        Resources resources = getResources() ;

        drawCircularBg(canvas, width, height, OFFSET, resources) ;
        drawPointer(canvas, width, height, OFFSET, resources) ;
    }

    private void drawCircularBg (Canvas canvas, int width, int height,
                                 final int OFFSET, final Resources resources) {

        int px = (width - OFFSET)/2 ;
        int py = (height - OFFSET)/2 ;
        int radius = Math.min(px, py) ;

        // Draw background
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        circlePaint.setColor(resources.getColor(R.color.compass_view_circle_bg_color));
        circlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(px + OFFSET/2, py + OFFSET/2, radius, circlePaint);

        // Draw Stroke
        circlePaint.setStrokeWidth(resources.getInteger(R.integer.compass_view_circle_stroke_width));
        circlePaint.setColor(resources.getColor(R.color.compass_view_circle_stroke_color));
        circlePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(px + OFFSET/2, py + OFFSET/2, radius, circlePaint);
    }

    private void drawPointer (Canvas canvas, int width, int height,
                              final int OFFSET, final Resources resources) {

        final int POINTER_CIRCLE_HEIGHT_DIFF = 200;
        int pointerWidth = (width - OFFSET)/5 ;
        int pointertopx =  (width - OFFSET)/2 - pointerWidth/2 ;
        int pointerHeight = (height - OFFSET - POINTER_CIRCLE_HEIGHT_DIFF) ;
        int pointertopy = (height - OFFSET)/2 - pointerHeight/2 ;

        Paint pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;

        /*
        pointerPaint.setColor(Color.WHITE);
        pointerPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(pointertopx, pointertopy,
                pointertopx + pointerWidth, pointertopy + pointerHeight,
                pointerPaint);


        float[] baseTriangleCoords = { halfTopWidthX, halfTopWidthY,
                                        bottomLeftX, bottomLeftY,
                                        bottomMiddleX, bottomMiddleY } ;
        */

        int halfTopWidthX = pointertopx + pointerWidth / 2 ;
        int halfTopWidthY = pointertopy ;

        int bottomLeftX = pointertopx ;
        int bottomLeftY = pointertopy + pointerHeight ;

        int bottomMiddleX = pointertopx + pointerWidth / 2 ;
        int bottomMiddleY = halfTopWidthY + pointerHeight - pointerHeight/6 ;

        int bottomRightX = pointertopx + pointerWidth ;
        int bottomRightY = pointertopy + pointerHeight ;

        int twoThirdMiddleX = pointertopx + pointerWidth / 2;
        int twoThirdMiddleY = pointertopy + pointerHeight/3 * 2 ;

        int twoThirdLeftX = pointertopx + (halfTopWidthX - pointertopx)/3 ;
        int twoThirdLeftY = pointertopy + pointerHeight/3 * 2 ;

        int twoThirdRightX = halfTopWidthX + pointerWidth/2 * 2/3 ;
        int twoThirdRightY = pointertopy + pointerHeight/3 * 2 ;

        int thirdMiddleX = pointertopx + pointerWidth / 2;
        int thirdMiddleY = pointertopy + pointerHeight / 5  ;

        int thirdLeftX = pointertopx + (halfTopWidthX - pointertopx) /5 * 4 ;
        int thirdLeftY = pointertopy + pointerHeight / 5 ;

        int thirdRightX = halfTopWidthX + pointerWidth/2 * 1/5 ;
        int thirdRightY = pointertopy + pointerHeight / 5 ;

        // Draw base left triangle
        pointerPaint.setColor(resources.getColor(R.color.left_triangle_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseLeftTrianglePath = new Path() ;
        baseLeftTrianglePath.setFillType(Path.FillType.EVEN_ODD);
        baseLeftTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseLeftTrianglePath.lineTo(bottomLeftX, bottomLeftY);
        baseLeftTrianglePath.lineTo(bottomMiddleX, bottomMiddleY);
        baseLeftTrianglePath.close();
        canvas.drawPath(baseLeftTrianglePath, pointerPaint);

        // Draw base right triangle
        pointerPaint.setColor(resources.getColor(R.color.right_triangle_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseRightTrianglePath = new Path() ;
        baseRightTrianglePath.setFillType(Path.FillType.EVEN_ODD);
        baseRightTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseRightTrianglePath.lineTo(bottomRightX, bottomRightY);
        baseRightTrianglePath.lineTo(bottomMiddleX, bottomMiddleY);
        baseRightTrianglePath.close();
        canvas.drawPath(baseRightTrianglePath, pointerPaint);

        // draw middle left triangle
        pointerPaint.setColor(resources.getColor(R.color.left_triangle_middle_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseLeftMiddleTrianglePath = new Path() ;
        baseLeftMiddleTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseLeftMiddleTrianglePath.lineTo(twoThirdMiddleX, twoThirdMiddleY);
        baseLeftMiddleTrianglePath.lineTo(twoThirdLeftX, twoThirdLeftY);
        baseLeftMiddleTrianglePath.close();
        canvas.drawPath(baseLeftMiddleTrianglePath, pointerPaint);

        // draw middle right triangle
        pointerPaint.setColor(resources.getColor(R.color.right_triangle_middle_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseRightMiddleTrianglePath = new Path() ;
        baseRightMiddleTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseRightMiddleTrianglePath.lineTo(twoThirdMiddleX, twoThirdMiddleY);
        baseRightMiddleTrianglePath.lineTo(twoThirdRightX, twoThirdRightY);
        baseRightMiddleTrianglePath.close();
        canvas.drawPath(baseRightMiddleTrianglePath, pointerPaint);

        // draw upper middle left triangle
        pointerPaint.setColor(resources.getColor(R.color.left_triangle_top_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseLeftTopTrianglePath = new Path() ;
        baseLeftTopTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseLeftTopTrianglePath.lineTo(thirdMiddleX, thirdMiddleY);
        baseLeftTopTrianglePath.lineTo(thirdLeftX, thirdLeftY);
        baseLeftTopTrianglePath.close();
        canvas.drawPath(baseLeftTopTrianglePath, pointerPaint);

        // draw upper middle right triangle
        pointerPaint.setColor(resources.getColor(R.color.right_triangle_top_base_color));
        pointerPaint.setStyle(Paint.Style.FILL);
        Path baseRightTopTrianglePath = new Path() ;
        baseRightTopTrianglePath.moveTo(halfTopWidthX, halfTopWidthY);
        baseRightTopTrianglePath.lineTo(thirdMiddleX, thirdMiddleY);
        baseRightTopTrianglePath.lineTo(thirdRightX, thirdRightY);
        baseRightTopTrianglePath.close();
        canvas.drawPath(baseRightTopTrianglePath, pointerPaint);
    }
}
