package top.i7un.springboot.mytest.suanfa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noone on 2020-09-14.
 * 回溯算法 适用于好多 组合的问题
 *
 * //找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 * //
 * // 说明：
 * //
 * //
 * // 所有数字都是正整数。
 * // 解集不能包含重复的组合。
 * //
 * //
 * // 示例 1:
 * //
 * // 输入: k = 3, n = 7
 * //输出: [[1,2,4]]
 * //
 * //
 * // 示例 2:
 * //
 * // 输入: k = 3, n = 9
 * //输出: [[1,2,6], [1,3,5], [2,3,4]]
 * //
 */
public class huisu {

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> list = new ArrayList<>();
        getSum(list, new ArrayList<>(), k, n,1);
        return list;
    }

    public static void getSum(List<List<Integer>> ret,List<Integer> list,int k,int n,int start){
        if (k==0 || n <= 0){
            if (k==0 && n==0){
                ret.add(new ArrayList<>(list));
            }
            return;
        }

        for (int i = start; i <= 9; i++) {
            if(n<i){
                continue;
            }
            list.add(i);
            getSum(ret, list, k-1, n-i,i+1);
            list.remove(list.size()-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combinationSum3(3, 9));
    }
}
