#include <iostream>
#include <queue>

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
    static const int MAXV = 200;
    int nvertices, nedges;
    bool visited[MAXV];
    int colors[MAXV];
    bool bicolor;
    edgenode *edges[MAXV];

    graph (int nv=MAXV) {
        int i;
        nvertices = nv;
        nedges = 0;
        bicolor = true;

        for (i = 0; i < MAXV; i++) {
            visited[i] = false;
            colors[i] = -1;
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

    void color(int start, int c) {
        int v;
        queue<int> q;
        edgenode *p;

        q.push(start);
        visited[start] = true;
        colors[start] = c;

        while (!q.empty()) {
            v = q.front();
            q.pop();

            c = (c + 1) % 2;
            p = edges[v];
            while (p != NULL) {
                int w = p->w;
                if (!visited[w]) {
                    q.push(w);
                    visited[w] = true;
                    colors[w] = c;
                } else if (colors[w] == colors[v]) {
                    bicolor = false;
                }
                p = p->next;
            }
        }
    }
};

int main() {
    int nvertices, nedges;
    int i, from, to;
    graph *g;

    while (cin >> nvertices) {
        if (nvertices == 0)
            break;

        g = new graph(nvertices);
        cin >> nedges;

        for (i = 0; i < nedges; i++) {
            cin >> from >> to;
            g->insert_edge(from, to);
        }

        g->color(0, 0);
        if (g->bicolor) {
            cout << "BICOLORABLE." << endl;
        } else {
            cout << "NOT BICOLORABLE." << endl;
        }

        delete g;
    }
    return 0;
}
