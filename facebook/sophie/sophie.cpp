/*
 * =====================================================================================
 *
 *       Filename:  sophie.cpp
 *
 *    Description:  Facebook Puzzle Finding Sophie - Similar to Travelling Sales Person,
 *                  use Floyd-Warshall for finding all pairs shortest distance, then
 *                  backtracking plus high level pruning.
 *
 *        Version:  1.0
 *        Created:  14/04/11 10:58:52
 *       Revision:  none
 *       Compiler:  g++
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */


#include <iostream>
#include <string>
#include <map>
#include <vector>
#include <algorithm>
#include <fstream>
#include <climits>
#include <cfloat>
#include <cstdio>

using namespace std;

const int MAXD = 999999;

struct next {
    string place;
    double expt;
};

bool compare_exp (const next &n1, const next &n2) {
    return n1.expt < n2.expt;
}

void backtrack(map<string, pair<int, double> > places,
        map<string, pair<int, double> > pmap,
        vector<vector<int> > spt, double total_prob,
        double &best_expt, double cur_expt,
        string fplace, string tplace) {
    int from = places[fplace].first;
    int to = places[tplace].first;
    double prob = places[tplace].second;
    int i;

    if (spt[from][to] == MAXD && prob > 0.0)
        return;

    double expt = spt[from][to] * total_prob;

    if ((cur_expt + expt) > best_expt && best_expt != -1) {
        return;
    }

    pmap.erase(tplace);
    if (pmap.empty()) {
        best_expt = cur_expt + expt;
        return;
    }

    map<string, pair<int, double> >::iterator it;
    vector<next> list;
    next n;
    for (it = pmap.begin(); it != pmap.end(); ++it) {
        n.place = it->first;
        n.expt = it->second.second * spt[to][it->second.first];
        list.push_back(n);
    }

    sort(list.begin(), list.end(), compare_exp);
    for (i = 0; i < list.size(); i++) {
        backtrack(places, pmap, spt, total_prob - prob,
                best_expt, cur_expt + expt,
                tplace, list[i].place);
    }
}

int main(int argc, char *argv[]) {
    int nplaces, npaths;
    map<string, pair<int, double> > places;
    string place, fplace, tplace, start;
    double probability;
    int time;
    vector<vector<int> > spt;
    vector<vector<bool> > closure;
    int i, j, s, t;
    double best_expt = -1;

    ifstream input(argv[1]);

    input >> nplaces;
    for (i = 0; i < nplaces; i++) {
        input >> place >> probability;
        if (i == 0)
            start = place;

        if (places.find(place) == places.end()) {
            places[place] = make_pair(i, probability);
        }
    }

    spt.resize(nplaces);
    closure.resize(nplaces);
    for (i = 0; i < nplaces; i++) {
        spt[i].resize(nplaces);
        closure[i].resize(nplaces);
        for (j = 0; j < nplaces; j++) {
            if (i == j) {
                spt[i][j] = 0;
                closure[i][j] = true;
            } else {
                spt[i][j] = MAXD;
                closure[i][j] = false;
            }
        }
    }

    int from, to;
    input >> npaths;
    for (i = 0; i < npaths; i++) {
        input >> fplace >> tplace >> time;
        from = places[fplace].first;
        to = places[tplace].first;

        spt[from][to] = time;
        spt[to][from] = time;
        closure[from][to] = true;
        closure[to][from] = true;
    }

    // Calculate all pairs shortest paths
    for (i = 0; i < nplaces; i++) {
        for (s = 0; s < nplaces; s++) {
            if (closure[s][i]) {
                for (t = 0; t < nplaces; t++) {
                    if (s != t) {
                        if (spt[s][t] > spt[s][i] + spt[i][t]) {
                            closure[s][t] = closure[s][i];
                            spt[s][t] = spt[s][i] + spt[i][t];
                        }
                    }
                }
            }
        }
    }

    map<string, pair<int, double> > pmap = places;
    map<string, pair<int, double> >::iterator it;
    pmap.erase(start);
    vector<next> list;
    next n;
    double total_prob = 0.0;
    for (it = pmap.begin(); it != pmap.end(); ++it) {
        n.place = it->first;
        n.expt = it->second.second * spt[places[start].first][it->second.first];
        total_prob += it->second.second;
        list.push_back(n);
    }

    sort(list.begin(), list.end(), compare_exp);
    for (i = 0; i < list.size(); i++) {
        backtrack(places, pmap, spt, total_prob, best_expt, 0.0, start,
                list[i].place);
    }

    printf("%.2f\n", best_expt);
}
