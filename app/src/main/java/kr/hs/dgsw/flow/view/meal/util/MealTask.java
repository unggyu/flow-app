package kr.hs.dgsw.flow.view.meal.util;

import android.os.AsyncTask;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018-03-20.
 */

public class MealTask extends AsyncTask<Void, Void, List<SchoolMenu>> {
    private Callback callback;
    private School school;

    private int year;
    private int month;

    public MealTask(School school, int year, int month, Callback callback) {
        this.callback = callback;
        this.school = school;

        this.year = year;
        this.month = month;
    }

    @Override
    protected List<SchoolMenu> doInBackground(Void... voids) {
        List<SchoolMenu> menus = null;

        try {
            menus = school.getMonthlyMenu(year, month + 1);
        } catch (SchoolException e) {
            callback.onFailure(e);
        }

        return menus;
    }

    @Override
    protected void onPostExecute(List<SchoolMenu> schoolMenus) {
        super.onPostExecute(schoolMenus);
        if (schoolMenus != null) {
            callback.onSuccess(schoolMenus);
        }
    }

    public interface Callback {
        void onSuccess(List<SchoolMenu> responseMenuList);
        void onFailure(SchoolException e);
    }
}
