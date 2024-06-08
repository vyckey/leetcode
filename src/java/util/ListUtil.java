package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ListUtil {
    private ListUtil() {
    }

    public static List<Integer> randomList(int size, int minIncluded, int maxExcluded) {
        Random random = new Random();
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(minIncluded, maxExcluded));
        }
        return list;
    }

    public static <T> List<List<T>> toList(T[][] matrix) {
        List<List<T>> result = new ArrayList<>(matrix.length);
        for (T[] row : matrix) {
            result.add(Arrays.asList(row));
        }
        return result;
    }

    public static boolean deepEquals(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.equals(list2)) {
                return false;
            }
        }
        return true;
    }
}
