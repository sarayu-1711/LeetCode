
import java.util.*;

class Solution {
    public List<String> commonChars(String[] words) {

        List<String> result = new ArrayList<>();

        int[] common = new int[26];
        Arrays.fill(common, Integer.MAX_VALUE);

        for (String word : words) {

            int[] count = new int[26];

            for (char ch : word.toCharArray()) {
                count[ch - 'a']++;
            }

            for (int i = 0; i < 26; i++) {
                common[i] = Math.min(common[i], count[i]);
            }
        }

        for (int i = 0; i < 26; i++) {
            while (common[i] > 0) {
                result.add("" + (char)(i + 'a'));
                common[i]--;
            }
        }

        return result;
    }
}
