#include <cstdio>
#include <iostream>
#include <string>
#include <map>
#include <queue>
#include <stack>
#include <vector>

using namespace std;

const int MAXD = 25143;

struct edgenode {
    int w;
    edgenode *next;

    edgenode (int v) {
        w = v;
        next = NULL;
    }
};

struct graph {
    static const int MAXV = 1000;
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

    void print_path(int start, vector<string> words) {
        stack<int> s;
        int size = 0;
        int v = parent[start];

        if (v == -1) {
            cout << "No solution." << endl;
            return;
        }

        s.push(start);
        while (v != -1) {
            s.push(v);
            v = parent[v];
        }

        while (!s.empty()) {
            v = s.top();
            s.pop();
            cout << words[v] << endl;
        }
    }
};

bool is_doublet(string a, string b) {
    int i;
    bool doublet = true;

    int alen = a.length();
    int blen = b.length();

    if (alen != blen) {
        if (alen > blen) {
            int tmp = alen;
            alen = blen;
            blen = tmp;
        }

        for (i = 0; i < alen; i++) {
            if (a[i] != b[i]) {
                doublet = false;
                break;
            }
        }
    } else {
        int ndiff = 0;
        for (i = 0; i < alen; i++)
            if (a[i] != b[i])
                ndiff++;

        doublet = (ndiff <= 1);
    }

    return doublet;
}

int main() {
    map<string, int> dict;
    vector<string> words;
    char buf[16];
    graph *g;
    string line, first, second;
    int id = 0, i, j;

    getline(cin, line);
    while (line != "") {
        sscanf(line.c_str(), "%[a-z]", buf);
        dict[buf] = id++;
        words.push_back(buf);
        getline(cin, line);
    }

    g = new graph(words.size());
    for (i = 0; i < (int)words.size(); i++) {
        for (j = (i + 1); j < (int)words.size(); j++) {
            if (is_doublet(words[i], words[j])) {
                g->insert_edge(dict[words[i]], dict[words[j]]);
            }
        }
    }

    while (cin >> first >> second) {
        g->bfs(dict[first]);
        g->print_path(dict[second], words);
        cout << endl;
    }
}
