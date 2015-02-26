package kristofcolpaert.com.week3oefening3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by kristofcolpaert on 26/02/15.
 */
public class ColorView extends View
{
    private String color = "#FFFFFF";
    private Paint paint;
    private Rect rect;

    /*
    Constructor
     */
    public ColorView(Context context) {
        super(context);

        initPaint();
        initRect();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initRect();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initRect();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    /*
    Getters and setters
     */

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
        this.paint.setColor(Color.parseColor(this.color));
        invalidate();
    }

    /*
        Methods
         */
    private void initRect()
    {
        this.rect = new Rect(0, 0, getWidth(), getHeight());
    }

    private void initPaint()
    {
        this.paint = new Paint();
        this.paint.setColor(Color.parseColor(this.color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rect.set(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, paint);
    }

    private void showColorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Pick a color").setItems(R.array.holo_colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectColor(which);
            }
        });
        builder.show();
    }

    protected void selectColor(int which)
    {
        switch(which)
        {
            case 0:
                setColor("#33B5E5");
                break;
            case 1:
                setColor("#AA66CC");
                break;
            case 2:
                setColor("#99CC00");
                break;
            case 3:
                setColor("#FFBB33");
                break;
            case 4:
                setColor("#FF4444");
                break;
            default:
                break;
        }
    }

    /*
    Events
     */

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString("color", this.color);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        this.color = bundle.getString("color", "#FFFFFF");
    }
}
