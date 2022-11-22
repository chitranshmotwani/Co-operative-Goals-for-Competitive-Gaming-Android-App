package ca.cmpt276.Calcium;

import android.app.Activity;

public class Utils {
    private static int sTheme;

    public final static int THEME = 0;
    public final static int THEME_1 = 1;
    public final static int THEME_2 = 2;
    public final static int THEME_3 = 3;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME:
                activity.setTheme(R.style.Theme);
                break;
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
