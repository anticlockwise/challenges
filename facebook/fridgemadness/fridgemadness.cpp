#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
#include <deque>
#include <cstdio>
#include <sstream>
#include <string>

using namespace std;

struct engineer {
    int id;
    vector<int> prefs;

    engineer(int i=0) {
        id = id;
    }
};

bool compare_id(const pair<int, int> &p1, const pair<int, int> &p2) {
    return p1.first < p2.first;
}

int op_getid(pair<int, int> p) {
    return p.first;
}

bool compare_pref(const pair<int, int> &p1, const pair<int, int> &p2) {
    bool smaller = false;

    if (p1.second < p2.second) {
        smaller = true;
    } else if (p1.second > p2.second) {
        smaller = false;
    } else {
        if (p1.first < p2.first) {
            smaller = true;
        }
    }
    return !smaller;
}

pair<int, int> score(engineer a, engineer b, int n) {
    int score_ab, score_ba;
    int i, j, base_a, base_b;
    pair<int, int> result;

    score_ab = score_ba = (n * (n + 1)) / 2;

    for (i = 0; i < n; i++) {
        base_a = n - i;
        for (j = 0; j < n; j++) {
            base_b = n - j;
            if (a.prefs[i] == b.prefs[j]) {
                if (i < j) {
                    score_ab += base_a;
                } else if (i > j) {
                    score_ba += base_b;
                } else {
                    score_ab += base_a * base_a;
                    score_ba += base_b * base_b;
                }
                score_ab -= base_a;
                score_ba -= base_b;
            }
        }
    }

    result.first = score_ab;
    result.second = score_ba;
    return result;
}

map<int, vector< pair<int, int> > > scores(
        map<int, engineer> drink_prefs, int nengineers) {
    int median = nengineers / 2;
    map<int, vector< pair<int, int> > > scores;
    engineer p1, p2;
    pair<int, int> sc, p;
    int i, j, n;

    for (int i = 0; i < median; i++) {
        p1 = drink_prefs[i];
        n = p1.prefs.size();
        for (j = median; j < nengineers; j++) {
            p2 = drink_prefs[j];
            sc = score(p1, p2, n);
            p.first = j;
            p.second = sc.first;
            scores[i].push_back(p);
            p.first = i;
            p.second = sc.second;
            scores[j].push_back(p);
        }
    }

    return scores;
}

map<int, deque<int> > prefs(map<int, vector< pair<int, int> > > scores,
        int nengineers) {
    int median = nengineers / 2;
    vector<pair<int, int> > slist;
    map<int, deque<int> > pref_map;
    int i;

    for (i = 0; i < median; i++) {
        deque<int> plist;
        slist = scores[i];
        plist.resize(slist.size());
        sort(slist.begin(), slist.end(), compare_pref);
        transform(slist.begin(), slist.end(), plist.begin(), op_getid);
        pref_map[i] = plist;
    }

    return pref_map;
}

bool is_id (pair<int, int> p, int id) {
    return p.first == id;
}

vector<pair<int, int> > matches(map<int, vector<pair<int, int> > > scores,
        map<int, deque<int> > prefs, int nengineers) {
    int median = nengineers / 2;
    int i;
    deque<int> pool;
    map<int, int> matches;
    map<int, int>::iterator mit;
    vector<pair<int, int> > slist;
    vector<pair<int, int> >::iterator it;
    vector<pair<int, int> > pairs;
    pair<int, int> pr;

    for (i = 0; i < median; i++) {
        pool.push_back(i);
    }

    while (!pool.empty()) {
        int hi = pool.front();
        pool.pop_front();
        int lo = prefs[hi].front();
        prefs[hi].pop_front();

        if (matches.find(lo) != matches.end()) {
            int existing = matches[lo];

            slist = scores[lo];
            it = search_n(slist.begin(), slist.end(), 1, hi, is_id);
            int pos = it - slist.begin();
            int a = scores[lo][pos].second;
            int ha = scores[lo][pos].first;

            it = search_n(slist.begin(), slist.end(), 1, existing, is_id);
            pos = it - slist.begin();
            int b = scores[lo][pos].second;
            int hb = scores[lo][pos].first;

            if (a > b || (a == b && hi > existing)) {
                matches[lo] = hi;
                pool.push_back(existing);
            } else {
                pool.push_back(hi);
            }
        } else {
            matches[lo] = hi;
        }
    }

    for (mit = matches.begin(); mit != matches.end(); mit++) {
        int a = mit->first;
        int b = mit->second;
        if (a > b) {
            pr.first = b;
            pr.second = a;
        } else {
            pr.first = a;
            pr.second = b;
        }

        pairs.push_back(pr);
    }

    return pairs;
}

int main() {
    int ndrinks, nengineers;
    string line;
    int i, n, id, pref;
    map<int, engineer> engineers;

    getline(cin, line);
    sscanf(line.c_str(), "%d %d", &nengineers, &ndrinks);

    for (i = 0; i < ndrinks; i++)
        getline(cin, line);

    for (i = 0; i < nengineers; i++) {
        engineer e;
        char *p;
        int pos;

        getline(cin, line);
        while ((pos = line.find(",", 0)) != -1)
            line.replace(pos, 1, " ");

        istringstream is(line);
        is >> id;
        e.id = id;

        while (!is.eof()) {
            is >> pref;
            e.prefs.push_back(pref);
        }

        engineers[id] = e;

        line.erase(line.begin(), line.end());
    }

    map<int, vector< pair<int, int> > > scores_map =
        scores(engineers, nengineers);
    map<int, deque<int> > pref_list = prefs(scores_map, nengineers);
    vector<pair<int, int> > pairs = matches(scores_map, pref_list,
            nengineers);

    sort(pairs.begin(), pairs.end(), compare_id);
    for (i = 0; i < pairs.size(); i++) {
        cout << pairs[i].first << " " << pairs[i].second << endl;
    }
}
