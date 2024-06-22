package problem;

import org.junit.Assert;
import org.junit.Test;

import util.RandomUtil;

public class P238ProductOfArrayExceptSelf {

    @Test
    public void test() {
        final int testCaseSize = 100;
        for (int i = 0; i < testCaseSize; i++) {
            int[] nums = new int[RandomUtil.nextInt(2, 10^5)];
            for (int j = 0; j < nums.length; j++) {
                nums[j] = RandomUtil.nextInt(-30, 31);
            }
            
            int[] expectProducts = productExceptSelfSimple(nums);
            int[] realProducts = productExceptSelf(nums);
            Assert.assertArrayEquals(expectProducts, realProducts);
        }
    }

    public int[] productExceptSelfSimple(int[] nums) {
        int[] products = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    product *= nums[j];
                }
                products[i] = product;
            }
        }
        return products;
    }

    /**
     * 设multi(i,j)表示从位置i到j的所有元素累乘乘积，则有
     * products(i)=multi(0,i-1)*multi(i+1,n)
     */
    public int[] productExceptSelf(int[] nums) {
        int[] products = new int[nums.length];
        products[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            products[i] = products[i - 1] * nums[i];
        }
        int postProduct = 1;
        for (int i = nums.length -1; i > 0; i--) {
            products[i] = products[i - 1] * postProduct;
            postProduct *= nums[i];
        }
        products[0] = postProduct;
        return products;
    }
}
