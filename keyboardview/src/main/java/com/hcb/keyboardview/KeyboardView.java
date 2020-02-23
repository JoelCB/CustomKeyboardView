package com.hcb.keyboardview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KeyboardView extends LinearLayout implements View.OnClickListener {
    private final String DEFAULT_PRIMARY_COLOR = "#666766";
    private final String DEFUALT_SECONDARY_COLOR = "#404040";

    private int type;
    private int height, width, fontSize;
    private int background, primaryColor, secundaryColor, primaryTextColor, secundaryTextColor;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public KeyboardView(Context context) {
        this(context, null, 0);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        importAttributes(context, attrs);
        init(context);
    }

    private void importAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyboardView, 0, 0);

        try {
            type = a.getInteger(R.styleable.KeyboardView_type, 0);

            Resources r = context.getResources();
            float defaultFontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, r.getDisplayMetrics());
            float defaultHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
            float defaultWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());

            fontSize = a.getDimensionPixelSize(R.styleable.KeyboardView_keyTextSize, (int) defaultFontSize);
            height = a.getDimensionPixelSize(R.styleable.KeyboardView_keyHeight, (int) defaultHeight);
            width = a.getDimensionPixelSize(R.styleable.KeyboardView_keyWidth, (int) defaultWidth);

            background = a.getInteger(R.styleable.KeyboardView_keyboardBackgroundColor, Color.parseColor("#1D1D1D"));
            primaryColor = a.getInteger(R.styleable.KeyboardView_primaryKeyColor, Color.parseColor(DEFAULT_PRIMARY_COLOR));
            secundaryColor = a.getInteger(R.styleable.KeyboardView_secondaryKeyColor, Color.parseColor(DEFUALT_SECONDARY_COLOR));
            primaryTextColor = a.getInteger(R.styleable.KeyboardView_primaryKeyTextColor, Color.WHITE);
            secundaryTextColor = a.getInteger(R.styleable.KeyboardView_secondaryKeyTextColor, Color.parseColor("#A3A2A2"));
        } finally {
            a.recycle();
        }
    }


    private void init(Context context) {
        ViewGroup view = null;

        if(type == 0) { // Email
            view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.component_email_keyboard, this, true);
        } else if(type == 1) { // Normal
            view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.component_email_keyboard, this, true);
        }

        setOnClicks(view);

        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_2, "2");
        keyValues.put(R.id.button_3, "3");
        keyValues.put(R.id.button_4, "4");
        keyValues.put(R.id.button_5, "5");
        keyValues.put(R.id.button_6, "6");
        keyValues.put(R.id.button_7, "7");
        keyValues.put(R.id.button_8, "8");
        keyValues.put(R.id.button_9, "9");
        keyValues.put(R.id.button_0, "0");
        keyValues.put(R.id.button_q, "q");
        keyValues.put(R.id.button_w, "w");
        keyValues.put(R.id.button_e, "e");
        keyValues.put(R.id.button_r, "r");
        keyValues.put(R.id.button_t, "t");
        keyValues.put(R.id.button_y, "y");
        keyValues.put(R.id.button_u, "u");
        keyValues.put(R.id.button_i, "i");
        keyValues.put(R.id.button_o, "o");
        keyValues.put(R.id.button_p, "p");
        keyValues.put(R.id.button_a, "a");
        keyValues.put(R.id.button_s, "s");
        keyValues.put(R.id.button_d, "d");
        keyValues.put(R.id.button_f, "f");
        keyValues.put(R.id.button_g, "g");
        keyValues.put(R.id.button_h, "h");
        keyValues.put(R.id.button_j, "j");
        keyValues.put(R.id.button_k, "k");
        keyValues.put(R.id.button_l, "l");
        keyValues.put(R.id.button_guion, "-");
        keyValues.put(R.id.button_z, "z");
        keyValues.put(R.id.button_x, "x");
        keyValues.put(R.id.button_c, "c");
        keyValues.put(R.id.button_v, "v");
        keyValues.put(R.id.button_b, "b");
        keyValues.put(R.id.button_n, "n");
        keyValues.put(R.id.button_m, "m");
        keyValues.put(R.id.button_arroba, "@");
        keyValues.put(R.id.button_punto, ".");
        keyValues.put(R.id.button_barra, "_");
        keyValues.put(R.id.button_puntoCom, ".com");
        keyValues.put(R.id.button_puntoEs, ".es");
        keyValues.put(R.id.button_gmail, "gmail.com");
        keyValues.put(R.id.button_hotmail, "hotmail.com");
        keyValues.put(R.id.button_outlook, "outlook.com");
        keyValues.put(R.id.button_yahoo, "yahoo.es");
    }

    private void setOnClicks(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            final View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                setOnClicks((ViewGroup) child);
            } else {
                if (child != null) {
                    child.setOnClickListener(this);
                    applyStyle(child);
                }
            }
        }
    }

    private void applyStyle(View view) {
        try {
            if (view instanceof Button || view instanceof ImageButton) {
                if (view instanceof Button) {
                    Button button = (Button) view;
                    button.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                }

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = height;

                getRootView().setBackgroundTintList(ColorStateList.valueOf(background));

                if(view.getBackgroundTintList() != null) {
                    if (view.getBackgroundTintList().getDefaultColor() == Color.parseColor(DEFAULT_PRIMARY_COLOR)) {
                        view.setBackgroundTintList(ColorStateList.valueOf(primaryColor));

                        if (view instanceof Button) {
                            ((Button) view).setTextColor(ColorStateList.valueOf(primaryTextColor));
                        } else {
                            ((ImageButton) view).setImageTintList(ColorStateList.valueOf(primaryTextColor));
                        }
                    } else if (view.getBackgroundTintList().getDefaultColor() == Color.parseColor(DEFUALT_SECONDARY_COLOR)) {
                        view.setBackgroundTintList(ColorStateList.valueOf(secundaryColor));

                        if (view instanceof Button) {
                            ((Button) view).setTextColor(ColorStateList.valueOf(secundaryTextColor));
                        } else {
                            ((ImageButton) view).setImageTintList(ColorStateList.valueOf(secundaryTextColor));
                        }
                    } else {
                        layoutParams.width = LayoutParams.MATCH_PARENT;
                        if (view instanceof Button) {
                            ((Button) view).setTextColor(ColorStateList.valueOf(secundaryTextColor));
                        }
                    }
                } else {
                    layoutParams.width = LayoutParams.MATCH_PARENT;
                    if (view instanceof Button) {
                        ((Button) view).setTextColor(ColorStateList.valueOf(secundaryTextColor));
                    }
                }

                if (view.getId() == R.id.button_delete || view.getId() == R.id.button_puntoCom || view.getId() == R.id.button_puntoEs) {
                    layoutParams.width = (int) (((float) width) * 1.5);
                }

                view.setLayoutParams(layoutParams);
            } else {
                view.setBackgroundTintList(ColorStateList.valueOf(secundaryTextColor));
            }
        } catch (Exception e) {
            Log.e("KeyboardError", "Error ocurred: " + e.getMessage());
        }
    }

    private String st = "";

    @Override
    public void onClick(View view) {
        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (view.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                //inputConnection.commitText("", inputConnection);
            }
        } else {
            String value = keyValues.get(view.getId());
            st += value;
            inputConnection.setComposingText(value, 1);
        }
    }

    public void applyChanges(Context context) {
        init(context);
    }

    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKeyHeight() {
        return height;
    }

    public void setKeyHeight(int height) {
        this.height = height;
    }

    public int getKeyWidth() {
        return width;
    }

    public void setKeyWidth(int width) {
        this.width = width;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getKeyboardBackground() {
        return background;
    }

    public void setKeyboardBackground(int background) {
        this.background = background;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int getSecundaryColor() {
        return secundaryColor;
    }

    public void setSecundaryColor(int secundaryColor) {
        this.secundaryColor = secundaryColor;
    }

    public int getPrimaryTextColor() {
        return primaryTextColor;
    }

    public void setPrimaryTextColor(int primaryTextColor) {
        this.primaryTextColor = primaryTextColor;
    }

    public int getSecundaryTextColor() {
        return secundaryTextColor;
    }

    public void setSecundaryTextColor(int secundaryTextColor) {
        this.secundaryTextColor = secundaryTextColor;
    }
}