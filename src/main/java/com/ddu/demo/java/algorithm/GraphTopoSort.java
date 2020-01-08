package com.ddu.demo.java.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 假设 A 先于 B （B 依赖于 A），用有向图中的边 A->B 表示
 * @author wxl24life
 */
public class GraphTopoSort {

    static class Graph {
        private int v;
        private LinkedList<Integer> adj[];

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                adj[i] = new LinkedList<Integer>();
            }
        }

        public void addEdge(int s, int t) {
            adj[s].add(t);
        }

        public int size() {
            return v;
        }

        public List<Integer>[] getEdges() {
            return adj;
        }

    }

    public void topoSort(Graph G) {
        int v = G.size();
        List<Integer>[] adj = G.getEdges();

        // 用于记录图中所有顶点的入度
        int[] degree = new int[v];
        for (int i = 0; i < v; i++) {
            for (int j : adj[i]) {
                degree[j]++;
            }
        }

        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; i++) {
            // 入度为 0 的顶点表示它不再依赖于任何其他顶点
            if (degree[i] == 0) queue.addLast(i);
        }

        // bfs
        while (!queue.isEmpty()) {
            int node = queue.removeFirst();
            int size = adj[node].size();
            System.out.print("->" + node);
            while (size-- > 0) {
                int cur = adj[node].get(size);
                degree[cur]--;
                if (degree[cur] == 0) {
                    queue.addLast(cur);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(7);

        G.addEdge(0, 1);
        G.addEdge(1, 2);
        // G.addEdge(1, 3);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(4, 5);
        G.addEdge(3, 6);

        new GraphTopoSort().topoSort(G);
    }
}
