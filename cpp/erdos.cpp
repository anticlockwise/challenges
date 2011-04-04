/*
 * =====================================================================================
 *
 *       Filename:  erdos.cpp
 *
 *    Description:  UVa 10044 - Erdos Number
 *
 *        Version:  1.0
 *        Created:  04/03/2011 19:53:23
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */

#include <cstdio>
#include <iostream>
#include <vector>
#include <map>
#include <queue>
#include <cstring>
#include <string>

using namespace std;

struct author {
    string initial;
    string lastname;

    author(string init="", string lname="") {
        initial = init;
        lastname = lname;
    }

    bool operator < (author a) const {
        int cmp = lastname.compare(a.lastname);
        return (cmp == 0) ?
            (initial.compare(a.initial) < 0) : (cmp < 0);
    }
};

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

int main() {
    int ncases;
    int npapers, nauthors;
    string line;
    graph *g;
    author erdos("P.", "Erdos");
    map<author, int> author_id;
    int i, j, k, l;

    getline(cin, line);
    sscanf(line.c_str(), "%d", &ncases);

    for (i = 0; i < ncases; i++) {
        author au;
        char *p;
        char init_buf[10], buf[256];

        author_id[erdos] = 0;

        getline(cin, line);
        sscanf(line.c_str(), "%d %d", &npapers, &nauthors);
        g = new graph(nauthors + 1);

        for (j = 0; j < npapers; j++) {
            vector<int> relation;
            char left_buf[2048];
            int n = 0;
            int c = 0;

            getline(cin, line);
            while (true) {
                int t = line.find(",", c);
                if (t == string::npos) {
                    t = line.find(":", c);
                    au.initial = line.substr(c, t - c);

                    if (author_id.find(au) == author_id.end()) {
                        author_id[au] = author_id.size();
                    }

                    relation.push_back( author_id[au] );
                    break;
                }

                if (n == 0) {
                    au.lastname = line.substr(c, t - c);
                } else {
                    au.initial = line.substr(c, t - c);

                    if (author_id.find(au) == author_id.end()) {
                        author_id[au] = author_id.size();
                    }

                    relation.push_back( author_id[au] );
                }

                c = t + 2;
                n = (n + 1) % 2;
            }

            for (k = 0; k < (int)relation.size(); k++) {
                for (l = k + 1; l < (int)relation.size(); l++) {
                    g->insert_edge(relation[k], relation[l]);
                }
            }
        }

        g->bfs( author_id[erdos] );

        for (j = 0; j < nauthors; j++) {
            getline(cin, line);
            p = (char *)line.c_str();
            sscanf(p, "%[A-Za-z]%*[, ]%[A-Z.]", buf, init_buf);
            au.initial = init_buf;
            au.lastname = buf;

            cout << au.lastname << ", " << au.initial << " ";
            int s = g->path_size(author_id[au]);
            if (s == -1) {
                cout << "infinity";
            } else {
                cout << s;
            }
            cout << endl;
        }

        delete g;
        author_id.clear();
    }
}
