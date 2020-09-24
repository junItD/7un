package top.i7un.springboot.mytest.suanfa;

import java.util.HashMap;

/**
 * Created by Noone on 2020-09-14.
 * 滑动窗口  LeetCode第三题
 *
 * //给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * //
 * // 示例 1:
 * //
 * // 输入: "abcabcbb"
 * //输出: 3
 * //解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * //
 * //
 * // 示例 2:
 * //
 * // 输入: "bbbbb"
 * //输出: 1
 * //解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * //
 * //
 * // 示例 3:
 * //
 * // 输入: "pwwkew"
 * //输出: 3
 * //解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * //     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int ans = 0;
        for (int end = 0,start = 0; end < s.length(); end++) {
            if (map.keySet().contains(s.charAt(end))){
                ans = Math.max(ans, end -start);
                start = Math.max(map.get(s.charAt(end)), start); //找到下一个相同元素的位置 即下一个start的位置
            }
            map.put(s.charAt(end),end+1);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}
