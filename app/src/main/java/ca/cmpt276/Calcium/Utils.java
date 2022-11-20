package ca.cmpt276.Calcium;

import android.app.Activity;

public class Utils {
    private static int sTheme;

    public final static int THEME_1 = 0;
    public final static int THEME_2 = 1;
    public final static int THEME_3 = 2;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_1:
                activity.setTheme(R.style.Theme1);
                break;
            case THEME_2:
                activity.setTheme(R.style.Theme2);
                break;
            case THEME_3:
                activity.setTheme(R.style.Theme3);
                break;
        }
    }
}
