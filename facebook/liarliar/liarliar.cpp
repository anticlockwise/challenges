/*
 * =====================================================================================
 *
 *       Filename:  liarliar.cpp
 *
 *    Description:  Facebook Puzzle Liar Liar: Bicoloring (Graph BFS)
 *
 *        Version:  1.0
 *        Created:  14/04/11 10:56:04
 *       Revision:  none
 *       Compiler:  g++
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */


#include <iostream>
#include <fstream>
#include <map>
#include <queue>
#include <cstdio>
#include <string>

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
    static const int MAXV = 1000000;
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

    void bfs(int start) {
        edgenode *p;
        queue<int> q;
        q.push(start);
        visited[start] = true;

        while (!q.empty()) {
            int v = q.front();
            q.pop();

            p = edges[v];
            while (p != NULL) {
                int w = p->w;
                if (!visited[w]) {
                    visited[w] = true;
                    colors[w] = (colors[v] + 1) % 2;
                    q.push(w);
                }

                p = p->next;
            }
        }
    }
};

int insert_name(map<string, int> &id_map, string name) {
    int id;
    if (id_map.find(name) == id_map.end()) {
        id = id_map.size();
        id_map[name] = id;
    } else {
        id = id_map[name];
    }
    return id;
}

int main(int argc, char *argv[]) {
    int nveterans, naccuses, nliars=0, ntruths=0;
    int i, j;
    string line;
    char buf[1024];
    map<string, int> id_map;
    map<string, int>::iterator it;
    graph *g;

    ifstream input(argv[1]);

    getline(input, line);
    sscanf(line.c_str(), "%d", &nveterans);
    g = new graph(nveterans);

    for (i = 0; i < nveterans; i++) {
        getline(input, line);
        sscanf(line.c_str(), "%s %d", buf, &naccuses);
        int from = insert_name(id_map, buf);

        for (j = 0; j < naccuses; j++) {
            getline(input, line);
            sscanf(line.c_str(), "%s", buf);
            int to = insert_name(id_map, buf);

            g->insert_edge(from, to);
        }
    }

    for (it = id_map.begin(); it != id_map.end(); it++) {
        int v = it->second;
        if (!g->visited[v]) {
            g->colors[v] = 0;
            g->bfs(v);
        }
    }

    for (i = 0; i < nveterans; i++) {
        if (g->colors[i] == 1)
            nliars++;
    }

    ntruths = nveterans - nliars;
    if (ntruths < nliars)
        cout << nliars << " " << ntruths << endl;
    else
        cout << ntruths << " " << nliars << endl;
}
