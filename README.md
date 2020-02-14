# Custom Keyboard View

This is a library for android developers of a keyboard which can be contained inside an activity like another view.
 
 <img src="https://raw.githubusercontent.com/JoelCB/CustomKeyboardView/master/demo.png"> 
 
## Gradle Dependency

Add this in your root build.gradle file at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency : 
```java
dependencies {
	        implementation 'com.github.JoelCB:CustomKeyboardView:1.0'
	}
```
Sync the gradle and that's it! :+1:

## Usage

### XML : 

```xml
 <com.hcb.keyboardview.KeyboardView
        android:id="@+id/keyboard"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:keyHeight="30dp"
        app:keyTextSize="10dp"
        app:keyWidth="30dp"
        app:primaryKeyColor="@color/colorPrimary"
        app:primaryKeyTextColor="@android:color/white"
        app:secondaryKeyColor="@color/colorPrimaryDark"
        app:secondaryKeyTextColor="@android:color/darker_gray"
        app:type="email" />
```

### Java :
```java
TextView txtOutput = findViewById(R.id.txtOutput);
KeyboardView keyboard = findViewById(R.id.keyboard);

keyboard.setOutput(txtOutput);

keyboard.setListener(new KeyboardListener() {
    @Override
    public void onAdd(KeyboardView keyboardView, String dataAdded, String newText, String oldText) {
        Log.i("Keyboard_OnAdd", "Added: " + dataAdded +" - New text: " + newText);
    }

    @Override
    public void onDelete(KeyboardView keyboardView, String newText, String oldText) {
        Log.i("Keyboard_OnDelete",  "New text: " + newText);
    }
});
```
