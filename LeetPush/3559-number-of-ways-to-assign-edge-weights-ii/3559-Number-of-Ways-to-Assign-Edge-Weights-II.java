import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;
    static final int LOG = 18;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int[][] parent = new int[LOG][n + 1];
        int[] depth = new int[n + 1];

        // BFS to calculate depth and immediate parent
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];

        queue.offer(1);
        visited[1] = true;
        parent[0][1] = 1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v : graph[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    depth[v] = depth[u] + 1;
                    parent[0][v] = u;
                    queue.offer(v);
                }
            }
        }

        // Binary lifting table
        for (int k = 1; k < LOG; k++) {
            for (int v = 1; v <= n; v++) {
                parent[k][v] =
                    parent[k - 1][parent[k - 1][v]];
            }
        }

        // Precompute powers of 2
        int[] pow2 = new int[n];

        pow2[0] = 1;

        for (int i = 1; i < n; i++) {
            pow2[i] = (int)(2L * pow2[i - 1] % MOD);
        }

        // Answer queries
        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];

            if (u == v) {
                answer[i] = 0;
                continue;
            }

            int lca = findLCA(u, v, depth, parent);

            int distance =
                depth[u] + depth[v] - 2 * depth[lca];

            answer[i] = pow2[distance - 1];
        }

        return answer;
    }

    // Find Lowest Common Ancestor
    private int findLCA(int u, int v,
                        int[] depth, int[][] parent) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // Make both nodes have equal depth
        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = parent[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        // Move both nodes upward
        for (int k = LOG - 1; k >= 0; k--) {
            if (parent[k][u] != parent[k][v]) {
                u = parent[k][u];
                v = parent[k][v];
            }
        }

        return parent[0][u];
    }
}