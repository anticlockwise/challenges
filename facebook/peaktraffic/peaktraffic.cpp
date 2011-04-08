#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <cstdio>
#include <map>
#include <bitset>
#include <vector>
#include <algorithm>

using namespace std;

const int MAXV = 10000;

struct edgenode {
    int w;
    edgenode *next;

    edgenode (int v) {
        w = v;
        next = NULL;
    }
};

struct graph {
    int nvertices, nedges;
    bool visited[MAXV];
    edgenode *edges[MAXV];

    graph (int nv=MAXV) {
        int i;
        nvertices = nv;
        nedges = 0;

        for (i = 0; i < MAXV; i++) {
            visited[i] = false;
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
};

int get_id(map<string, int> &email_map, string email) {
    int id;
    if (email_map.find(email) == email_map.end()) {
        id = email_map.size();
        email_map[email] = id;
    } else {
        id = email_map[email];
    }
    return id;
}

void print_graph(graph *g, map<string, int> ids) {
    map<string, int>::iterator it;
    edgenode *e;
    for (it = ids.begin(); it != ids.end(); it++) {
        int from = it->second;
        cout << from << ": ";
        e = g->edges[from];
        while (e != NULL) {
            cout << e->w << " ";
            e = e->next;
        }
        cout << endl;
    }
}

void print_bits(bitset<MAXV> bits, int n) {
    int i;
    for (i = 0; i < n; i++) {
        if (bits[i] == 1)
            cout << i << ",";
    }
    cout << endl;
}

void find_cliques(graph *g, bitset<MAXV> &inclique,
        bitset<MAXV> &vertices, bitset<MAXV> &notclique,
        int nvertices, vector<bitset<MAXV> > &mcliques) {
    int i, j;

    if (vertices.none() && notclique.none()) {
        if (inclique.count() > 2)
            mcliques.push_back(inclique);
        return;
    }

    edgenode *e;
    for (i = 0; i < nvertices; i++) {
        int ui = vertices[i];
        if (ui == 1) {
            bitset<MAXV> new_clique=inclique, new_vertices, new_notclique;
            bitset<MAXV> neighbours;

            e = g->edges[i];
            while (e != NULL) {
                neighbours[e->w] = 1;
                e = e->next;
            }

            vertices[i] = 0;

            new_clique[i] = 1;
            new_vertices = vertices & neighbours;
            new_notclique = notclique & neighbours;

            find_cliques(g, new_clique, new_vertices, new_notclique, nvertices,
                    mcliques);
            notclique[i] = 1;
        }
    }
}

int main(int argc, char *argv[]) {
    map<int, map<int, bool> > table;
    string line, part, email_from, email_to;
    map<string, int> email_map;
    map<int, string> emails;
    int i, j;
    graph *g;
    bitset<MAXV> vertices, clique, notclique;
    vector<bitset<MAXV> > result;

    ifstream input(argv[1]);

    g = new graph();
    while(getline(input, line)) {
        istringstream is(line);
        for (i = 0; i < 6; i++) {
            is >> part;
        }
        is >> email_from >> email_to;
        int id_from = get_id(email_map, email_from);
        int id_to = get_id(email_map, email_to);
        emails[id_from] = email_from;
        emails[id_to] = email_to;

        vertices[id_from] = 1;
        vertices[id_to] = 1;

        if (!table[id_from][id_to] && !table[id_to][id_from]) {
            table[id_from][id_to] = true;
        } else if (table[id_to][id_from] && !table[id_from][id_to]) {
            table[id_from][id_to] = true;
            g->insert_edge(id_from, id_to);
        }
    }

    //print_graph(g, email_map);
    int nemails = email_map.size();

    vector<string> results;
    find_cliques(g, clique, vertices, notclique, nemails, result);
    for (i = 0; i < result.size(); i++) {
        string res;
        vector<string> rlist;

        bitset<MAXV> r = result[i];
        for (j = 0; j < nemails; j++) {
            if (r[j] == 1) {
                rlist.push_back(emails[j]);
            }
        }

        sort(rlist.begin(), rlist.end());
        int s = rlist.size();
        for (j = 0; j < s; j++) {
            res += rlist[j] + ", ";
        }

        res.erase(res.size() - 2, 2);
        results.push_back(res);
    }

    sort(results.begin(), results.end());
    for (i = 0; i < results.size(); i++) {
        cout << results[i] << endl;
    }
}
