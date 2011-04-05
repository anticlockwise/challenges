#include <iostream>
#include <queue>
#include <map>
#include <stack>
#include <iterator>

using namespace std;

struct edgenode {
    int w;
    edgenode *next;

    edgenode (int v) {
        w = v;
        next = NULL;
    }
};

struct graph {
    static const int MAXV = 10000;
    int nvertices, nedges;
    bool visited[MAXV];
    int parent[MAXV];
    edgenode *edges[MAXV];

    graph (int nv=MAXV) {
        int i;
        nvertices = nv;
        nedges = 0;

        for (i = 0; i < MAXV; i++) {
            visited[i] = false;
            parent[i] = -1;
            edges[i] = NULL;
        }
    }

    void insert_edge(int v, int w) {
        edgenode *pw, *pv;

        pw = new edgenode(w);
        pw->next = edges[v];

        edges[v] = pw;

        pv = new edgenode(v);
        pv->next = edges[w];

        edges[w] = pv;
    }

    void bfs(int start) {
        int v;
        queue<int> q;
        edgenode *p;

        visited[start] = true;
        parent[start] = -1;
        q.push(start);

        while (!q.empty()) {
            v = q.front();
            q.pop();

            p = edges[v];
            while (p != NULL) {
                int w = p->w;
                if (!visited[w]) {
                    q.push(w);
                    visited[w] = true;
                    parent[w] = v;
                }
                p = p->next;
            }
        }
    }

    int path_size(int start) {
        int size = 0;
        int v = parent[start];

        if (v == -1)
            return -1;

        while (v != -1) {
            v = parent[v];
            size++;
        }

        return size;
    }
};

void int_to_arr(int n, int arr[4]) {
    int i = 0;

    for (i = 0; i < 4; i++)
        arr[i] = 0;
    i = 0;

    while (n != 0) {
        arr[i++] = n % 10;
        n /= 10;
    }
}

int arr_to_int(int arr[4]) {
    int sum = 0;
    int prod = 1;
    int i;

    for (i = 0; i < 4; i++) {
        sum += arr[i] * prod;
        prod *= 10;
    }

    return sum;
}

void initialize_graph(graph *g, bool restricts[]) {
    int i, j;
    int arr[4];
    for (i = 0; i <= 9999; i++) {
        if (restricts[i])
            continue;

        int_to_arr(i, arr);
        for (j = 0; j < 4; j++) {
            arr[j] += 1;
            if (arr[j] > 9) {
                arr[j] = 0;
            }
            int n = arr_to_int(arr);
            if (!restricts[n])
                g->insert_edge(i, arr_to_int(arr));

            arr[j] -= 1;
            if (arr[j] < 0) {
                arr[j] = 9;
            }

            arr[j] -= 1;
            if (arr[j] < 0) {
                arr[j] = 9;
            }
            n = arr_to_int(arr);
            if (!restricts[n])
                g->insert_edge(i, arr_to_int(arr));

            arr[j] += 1;
            if (arr[j] > 9) {
                arr[j] = 0;
            }
        }
    }
}

int main() {
    const int MAXN = 10000;
    graph *g;
    int ncases, nrestricts;
    int num[4];
    bool restricts[MAXN];
    int i, j, source, target;

    cin >> ncases;

    for (i = 0; i < ncases; i++) {
        for (j = 0; j < MAXN; j++)
            restricts[j] = false;

        g = new graph();
        cin >> num[3] >> num[2] >> num[1] >> num[0];
        source = arr_to_int(num);
        cin >> num[3] >> num[2] >> num[1] >> num[0];
        target = arr_to_int(num);

        cin >> nrestricts;
        for (j = 0; j < nrestricts; j++) {
            cin >> num[3] >> num[2] >> num[1] >> num[0];
            restricts[arr_to_int(num)] = true;
        }

        initialize_graph(g, restricts);

        g->bfs(source);
        cout << g->path_size(target) << endl;

        delete g;
    }
}
