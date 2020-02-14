package com.hcb.keyboardview;

public abstract class KeyboardListener {
    public abstract void onAdd(KeyboardView keyboardView, String dataAdded, String newText, String oldText);
    public abstract void onDelete(KeyboardView keyboardView, String newText, String oldText);
}
