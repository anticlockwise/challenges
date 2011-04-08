#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <map>

using namespace std;

const int MAXT = 1000;
const int MAXZ = 1000;

struct base {
    int number;
    int zergs;
    int gains;

    base (int n=0, int z=0, int g=0) {
        number = n;
        zergs = z;
        gains = g;
    }
};

bool feasible(int terrans, int zergs, int minerals) {
    if (minerals <= 1)
        return false;
    return log(minerals - 1) >= (-21.0 * zergs + 63.0 * terrans - 10.0);
}

int zergs_needed(int terrans, int minerals) {
    return ceil((63.0 * terrans - 10.0 - log(minerals - 1.0)) / 21);
}

int gain(int terrans, int zergs, int minerals) {
    double n = -63.0 * terrans + 21.0 * zergs + 10.0;
    double d = exp(n);
    double g = minerals * (d / (d + 1.0));
    double g_ceil = ceil(g);

    if (g_ceil - g > 0.5) {
        g = g_ceil - 1.0;
    } else {
        g = g_ceil;
    }
    return g;
}

void construct_candidates(vector<vector<base> > cand_map,
        vector<vector<base> > &candidates, vector<base> bases,
        int ibase, int nbases) {

    if (ibase == nbases) {
        candidates.push_back(bases);
        return;
    }

    vector<base> v = cand_map[ibase];
    int i;
    for (i = 0; i < v.size(); i++) {
        bases.push_back(v[i]);
        construct_candidates(cand_map, candidates, bases,
            ibase + 1, nbases);
        bases.pop_back();
    }
}

bool compare(const base &b1, const base &b2) {
    bool smaller = false;

    if (b1.zergs < b2.zergs) {
        smaller = true;
    } else if (b1.zergs == b2.zergs) {
        if (b1.gains > b2.gains) {
            smaller = true;
        }
    }
    return smaller;
}

bool compare_id(const base &b1, const base &b2) {
    return b1.number < b2.number;
}

vector<base> find_best(vector<base> cand, int nzergs) {
    vector<base> result;
    int table[MAXT + 1][MAXZ + 1];
    int base, zerg;
    int i, j, k;

    if (cand.empty())
        return result;

    for (i = 0; i < MAXT; i++) {
        for (j = 0; j < MAXZ; j++) {
            table[i][j] = 0;
        }
    }

    for (base = 1; base <= cand.size(); base++) {
        table[base][0] = 0;
        for (zerg = 1; zerg <= nzergs; zerg++) {
            if (cand[base-1].zergs <= zerg
                && ((cand[base-1].gains + table[base-1][zerg-cand[base-1].zergs]) > table[base-1][zerg])) {
                table[base][zerg] = cand[base-1].gains + table[base-1][zerg-cand[base-1].zergs];
            } else {
                table[base][zerg] = table[base-1][zerg];
            }
        }
    }

    i = cand.size();
    k = nzergs;
    while (i > 0 && k > 0) {
        if (table[i][k] != table[i-1][k]) {
            result.push_back(cand[i-1]);
            k -= cand[i-1].zergs;
        }
        i--;
    }

    return result;
}

int main() {
    int nplanets, nbases, nzergs, terrans, zergs, minerals, mcaptured;
    vector<vector<base> > cand_map;
    vector<vector<base> > candidates;
    vector<base> bases;
    base b;
    int i, j, k;

    cin >> nplanets;

    for (i = 0; i < nplanets; i++) {
        cin >> nbases >> nzergs;
        for (j = 0; j < nbases; j++) {
            cin >> terrans >> minerals;
            if (feasible(terrans, nzergs, minerals)) {
                zergs = zergs_needed(terrans, minerals);
                int last_gain = -1;
                vector<base> v;
                while (true) {
                    mcaptured = gain(terrans, zergs, minerals);
                    if (last_gain != -1 && mcaptured == last_gain)
                        break;

                    b.number = j;
                    b.zergs = zergs;
                    b.gains = mcaptured;
                    v.push_back(b);

                    last_gain = mcaptured;
                }
                cand_map.push_back(v);
                v.clear();
            }
        }

        construct_candidates(cand_map, candidates, bases, 0, cand_map.size());

        int best_zergs = -1, best_minerals = -1;
        vector<base> best_bases;
        for (j = 0; j < candidates.size(); j++) {
            vector<base> cand = candidates[j];
            sort(cand.begin(), cand.end(), compare);
            vector<base> best_cands = find_best(cand, nzergs);
            sort(best_cands.begin(), best_cands.end(), compare_id);

            int total_zergs = 0, total_minerals = 0;
            for (k = 0; k < best_cands.size(); k++) {
                total_zergs += best_cands[k].zergs;
                total_minerals += best_cands[k].gains;
            }
            if (best_minerals == -1 || total_minerals >= best_minerals) {
                best_zergs = total_zergs;
                best_minerals = total_minerals;
                best_bases = best_cands;
            }
        }

        cout << best_zergs << " " << best_minerals << endl;
        for (j = 0; j < best_bases.size(); j++) {
            cout << best_bases[j].number << " " << best_bases[j].zergs;
            if (j != best_bases.size() - 1)
                cout << " ";
        }
        cout << endl;

        //for (j = 0; j < candidates.size(); j++) {
            //for (k = 0; k < candidates[j].size(); k++) {
                //cout << candidates[j][k].number << ": " << candidates[j][k].zergs << endl;
            //}
            //cout << endl;
        //}

        //cout << "Candidates: =============================" << endl;
        //for (j = 0; j < cand_map.size(); j++) {
            //cout << j << ": " << endl;
            //for (k = 0; k < cand_map[j].size(); k++) {
                //cout << " * " << cand_map[j][k].zergs << " " << cand_map[j][k].gains << endl;
            //}
        //}
        //cout << endl;

        cand_map.clear();
        candidates.clear();
        bases.clear();
    }
}
