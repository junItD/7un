package top.i7un.springboot.mytest.suanfa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Noone on 2020-09-11.
 * //给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * //
 * // 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * //
 * //
 * //
 * // 示例:
 * //
 * // 给定 nums = [2, 7, 11, 15], target = 9
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.keySet().contains(target-nums[i])){
                return new int[]{i,map.get(target-nums[i])};
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{1, 2, 3, 4, 5}, 6)));
    }


}
