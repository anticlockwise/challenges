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

const int MAXV = 1000;

struct author {
    char initial;
    string lastname;

    author (char init=0, string lname="") {
        initial = init;
        lastname = lname;
    }

    bool operator < (author b) const {
        int cmp = lastname.compare(b.lastname);
        return (cmp == 0) ? initial < b.initial : cmp < 0;
    }
}

struct edgenode {
    int y;
    int weight;
    struct edgenode *next;
}

struct graph {
    edgenode *edges[MAXV+1];
    int degree[MAXV+1];
    int nvertices;
    int nedges;
    bool directed;

    graph (int nv, bool dir) {
        int i;

        nvertices = nv;
        directed = dir;
        nedges = 0;

        for (i = 0; i < MAXV; i++) {
            degree[i] = 0;
            edges[i] = NULL;
        }
    }

    void insert_edge(int x, int y, bool dir) {

    }
}

int main() {
    int ncases, npapers, nauthors;
    author erdos('P', "Erdos");
    map<author, int> author_id;
    map<author, int>::iterator it;
    string line;
    int i, j;

    cin >> ncases;

    for (i = 0; i < ncases; i++) {
        char *p;
        author au;
        char[256] buf;
        int n;

        author_id[erdos] = 0;

        cin >> npapers >> nauthors;
        for (j = 0; j < npapers; j++) {
            getline(cin, line);
            for (p = (char *)line.c_str();
                 sscanf(p, "%c. %[A-Za-z]%* [ ,.]%n", &au.initial, buf, &n) > 0; p += n) {
                au.lastname = buf;
                if (author_id.find(au) == author_id.end()) {
                    author_id[au] = author_id.size();
                }
            }
        }
    }
}
