package com.udacity.sandwichclub.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

// NOTE: I referred to StackOverflow to figure out how to transform a picture using picasso and ended up
// using a mixture of two different sources modified to fit my context found at the following URLs:
// "https://stackoverflow.com/questions/30704581/make-imageview-with-round-corner-using-picasso/30707381"
// and "https://stackoverflow.com/questions/27236158/adding-borders-for-image-rounded-image-android"
public class RoundCornersBorderTransform implements Transformation {

    private final int radius;
    private final int margin;

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundCornersBorderTransform(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {

        Paint paintRound = new Paint();
        paintRound.setAntiAlias(true);
        paintRound.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //draws a custom rectangle with rounded corners
        canvas.drawRoundRect(new RectF(0, 0, (source.getWidth() - margin), (source.getHeight() - margin)), radius, radius, paintRound);

        if (source != output) {
            source.recycle();
        }

        Paint paintBorder = new Paint();
        paintBorder.setColor(Color.BLACK);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setAntiAlias(true);
        paintBorder.setStrokeWidth(2);
        //draws the border
        canvas.drawRoundRect(new RectF(0, 0, (source.getWidth() - margin), (source.getHeight() - margin)), radius, radius, paintBorder);

        return output;
    }

    @Override
    public String key() {
        return "rounded_corners_with_border";
    }

}