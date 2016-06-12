package com.vickyleu.library.Base.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.vickyleu.library.R;


/**
 * Created by Administrator on 2016/5/23.
 */
public class DrawablePassword extends EditText {
    EditText ed;
    private Spannable.Factory spannableFactory;

    public DrawablePassword(Context context) {
        super(context);
        register();
    }

    Drawable drawable;

    public DrawablePassword(Context context, AttributeSet attrs) {
        super(context, attrs);
        register();
    }

    public DrawablePassword(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        register();
    }

    void register() {
        ed = this;
        ed.addTextChangedListener(new PasswordTextHelper(this));
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (watcher instanceof PasswordTextHelper) {
            super.addTextChangedListener(watcher);
        }
    }

    public void RefreshDrawable(CharSequence text, int start, int count, PasswordTextHelper watcher) {
        if (count == 0) {
            handler.removeCallbacksAndMessages(null);
            return;
        }
        ed.removeTextChangedListener(watcher);
        if (text.toString().contains("\n"))
            text = text.toString().replace("\n", "");
        ed.setText(getIconText(text, start, watcher));
        ed.addTextChangedListener(watcher);
        if (start + 1 > text.length()) {
            ed.setSelection(start);
            return;
        }
        ed.setSelection(start + 1);
        ed.requestFocus();
    }

    private Handler handler = new Handler();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        spannableFactory = Spannable.Factory
                .getInstance();
        drawable = getResources().getDrawable(R.drawable.dots);
        if (drawable != null) {
            drawable.setBounds(3, 3, 12, 12);
        }
    }

    public Spannable getIconText(CharSequence text, final int index, final PasswordTextHelper watcher) {
        final Spannable spannable = spannableFactory.newSpannable(text);
        if (index >= 1) {
            VerticalImageSpan imageSpan = new VerticalImageSpan(drawable);
            spannable.setSpan(imageSpan, index - 1, index,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            handler.removeCallbacksAndMessages(null);
            ed.removeCallbacks(null);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (watcher == null) return;
                Spannable spannable = spannableFactory.newSpannable(ed.getText());
                if (ed.getText().toString().length() < index || ed.getText().length() != spannable.length())
                    return;
                ed.removeTextChangedListener(watcher);
                try {
                    VerticalImageSpan imageSpan = new VerticalImageSpan(drawable);
                    spannable.setSpan(imageSpan, index, index + 1,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    ed.setText(spannable);
                    ed.setSelection(index + 1);
                } catch (Exception e) {
                }
                ed.addTextChangedListener(watcher);
                ed.requestFocus();
            }
        }, 300);
        return spannable;
    }

    public final void setPasswordChar() {
        this.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(R.string.login_only_can_input).toCharArray();
                return data;
            }
        });
    }

    public String getStringData(int id) {
        return getResources().getString(id);
    }

    public class VerticalImageSpan extends ImageSpan {
        public VerticalImageSpan(Drawable drawable) {
            super(drawable);
        }

        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fontMetricsInt) {
            Drawable drawable = getDrawable();
            Rect rect = drawable.getBounds();
            if (fontMetricsInt != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;
                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;
                fontMetricsInt.ascent = -bottom;
                fontMetricsInt.top = -bottom;
                fontMetricsInt.bottom = top;
                fontMetricsInt.descent = top;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            Drawable drawable = getDrawable();
            canvas.save();
            int transY = 0;
            transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }
    }
}
