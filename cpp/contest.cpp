/*
 * =====================================================================================
 *
 *       Filename:  contest.cpp
 *
 *    Description:  UVa 10258 - Contest Scoreboard
 *
 *        Version:  1.0
 *        Created:  04/04/2011 18:37:10
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
#include <string>
#include <map>
#include <vector>
#include <iterator>
#include <algorithm>

using namespace std;

const int MAXN = 100;
const int MAXP = 9;

struct contestant {
    int number;
    int penalty;
    int problems_solved;
    bool problems[MAXP];

    contestant(int n=-1) {
        number = n;
        penalty = 0;
        problems_solved = 0;

        int i;
        for (i = 0; i < MAXP; i++) {
            problems[i] = false;
        }
    }

    void solve_problem(int p, int time) {
        if (!problems[p]) {
            problems[p] = true;
            penalty += time;
            problems_solved++;
        }
    }
};

bool compare_contestants(const contestant &a, const contestant &b) {
    bool bigger = true;

    if (a.problems_solved < b.problems_solved) {
        bigger = false;
    } else if (a.problems_solved == b.problems_solved) {
        if (a.penalty < b.penalty) {
            bigger = false;
        } else if (a.penalty == b.penalty) {
            bigger = (a.number >= b.penalty);
        }
    }

    return bigger;
}

int main() {
    int ncases;
    string line;
    map<int, contestant> ct_map;
    map<int, contestant>::iterator it;
    vector<contestant> sort_list;
    vector<contestant>::iterator sit;
    int cid, problem, time;
    char result;
    int i;

    getline(cin, line);
    sscanf(line.c_str(), "%d", &ncases);
    getline(cin, line);

    for (i = 0; i < ncases; i++) {
        getline(cin, line);
        while (line != "") {
            sscanf(line.c_str(), "%d %d %d %c", &cid,
                    &problem, &time, &result);

            contestant *c;
            if (ct_map.find(cid) == ct_map.end()) {
                c = new contestant(cid);
                ct_map[cid] = *c;
                c = &ct_map[cid];
            } else {
                c = &ct_map[cid];
            }

            switch (result) {
                case 'I':
                    c->penalty += 20;
                    break;
                case 'C':
                    c->solve_problem(problem - 1, time);
                    break;
                default:
                    break;
            }
            getline(cin, line);
        }

        for (it = ct_map.begin(); it != ct_map.end(); it++) {
            sort_list.push_back(it->second);
        }
        sort(sort_list.begin(), sort_list.end(), compare_contestants);

        for (sit = sort_list.begin(); sit != sort_list.end(); sit++) {
            contestant c = *sit;
            cout << c.number << " " << c.problems_solved << " " << c.penalty << endl;
        }
        cout << endl;

        ct_map.clear();
        sort_list.clear();
    }
}
