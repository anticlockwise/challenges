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

    bool operator < (contestant c) const {
        bool smaller = false;

        if (problems_solved < c.problems_solved) {
            smaller = true;
        } else if (problems_solved == c.problems_solved) {
            if (penalty < c.penalty) {
                smaller = true;
            } else if (penalty == c.penalty) {
                smaller = (number < c.number);
            }
        }

        return smaller;
    }
};

int main() {
    int ncases;
    string line;
    map<int, contestant> ct_map;
    int cid, problem, time;
    char result;
    int i;

    getline(cin, line);
    sscanf(line.c_str(), "%d", &ncases);
    getline(cin, line);

    for (i = 0; i < ncases; i++) {
        getline(cin, line);
        while (line != "") {
            cout << line << endl;
            getline(cin, line);
            sscanf(line.c_str(), "%d %d %d %c", &cid,
                    &problem, &time, &result);

            contestant *c;
            if (ct_map.find(cid) == ct_map.end()) {
                c = new contestant(cid);
                ct_map[cid] = *c;
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
        }
        cout << endl;

        sort(ct_map.begin(), ct_map.end());
    }
}
