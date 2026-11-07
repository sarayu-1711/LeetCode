
import java.util.*;

class Solution {
    public int assignEdgeWeights(int[][] edges) {

        int n = edges.length + 1;

        List<Integer>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int[] depth = new int[n + 1];
        boolean[] visited = new boolean[n + 1];

        Queue<Integer> q = new LinkedList<>();

        q.add(1);
        visited[1] = true;

        int maxDepth = 0;

        while (!q.isEmpty()) {

            int u = q.poll();

            maxDepth = Math.max(maxDepth, depth[u]);

            for (int v : graph[u]) {

                if (!visited[v]) {
                    visited[v] = true;
                    depth[v] = depth[u] + 1;
                    q.add(v);
                }
            }
        }

        long ans = 1;
        long mod = 1000000007;

        for (int i = 0; i < maxDepth - 1; i++) {
            ans = (ans * 2) % mod;
        }

        return (int) ans;
    }
}
