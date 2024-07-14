package problem;

import org.junit.Assert;
import org.junit.Test;

public class P2591DistributeMoneyToMaximumChildren {
    @Test
    public void test() {
        Assert.assertEquals(-1, distMoney(2, 3));
        Assert.assertEquals(1, distMoney(20, 3));
        Assert.assertEquals(2, distMoney(16, 2));
        Assert.assertEquals(1, distMoney(17, 2));
        Assert.assertEquals(1, distMoney(23, 2));
    }

    /**
     * 8x+(n-x+z)=s => x=(s-n-z)/7 (z>=0)
     */
    public int distMoney(int money, int children) {
        if (money < children) {
            return -1;
        }

        int value = Math.min((money - children) / 7, children);
        int remain = money - value * 8;
        if (value == children && remain != 0) {
            value--;
        } else if (value + 1 == children && remain == 4) {
            value--;
        }
        return value < 0 ? -1 : value;
    }
}
