package common;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtil {
    private static final int[] DAY_OF_MONTH = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    @Test
    public void test() {
        int[][] cases = new int[][]{
            {1970, 1, 1},
            {2016, 3, 1},
            {2024, 2, 1},
            {2024, 2, 29},
            {2024, 3, 3},
            {3000, 5, 1},
            {3001, 3, 1}
        };

        for (int[] arr : cases) {
            int year = arr[0], month = arr[1], day = arr[2];
            LocalDate date = LocalDate.of(year, month, day);
            // System.out.println(date);
            int[] res = compute(year, month, day);
            System.out.println(date.getDayOfYear() + ":"+ res[0] +"\t" + date.getDayOfWeek() +":" + res[1]);
        }
    }

    public int[] compute(int year, int month, int day) {
        boolean isBonusYear = isBonusYear(year);
        int dayth = dayth(month, day, isBonusYear);
        int totalDays = (year - 1970) * 365 + dayth;

        year--;
        int x = (year - 1968) / 4;
        int y = (year - 1900) / 100;
        int z = (year - 1600) / 400;
        int bonusDays = x + z - y;
        // System.out.println(x + " z"+ z +" y"+y);
        totalDays += bonusDays;

        int week = (totalDays + 3) % 7;
        return new int[]{dayth, week};
    }

    private int dayth(int month, int day, boolean bonusYear) {
        int dayth = day;
        for (int m = 1; m < month; m++) {
            dayth += DAY_OF_MONTH[m - 1];
        }
        if (bonusYear && month > 2) {
            dayth++;
        }
        return dayth;
    }

    private boolean isBonusYear(int year) {
        if ((year % 4) != 0) {
            return false;
        }
        if ((year % 400) == 0) {
            return true;
        }
        return (year % 100) != 0;
    }
}