/*
 * =====================================================================================
 *
 *       Filename:  usrbincrash.cpp
 *
 *    Description:  Facebook Puzzle User Bin Crash: Dynamic Programming
 *
 *        Version:  1.0
 *        Created:  14/04/11 11:02:47
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
#include <algorithm>
#include <vector>
#include <cstdio>
#include <string>
#include <climits>

using namespace std;

struct cargo {
    string name;
    int weight;
    int value;

    cargo (string n="", int w=0, int v=0) {
        name = n;
        weight = w;
        value = v;
    }
};

int gcd(int x, int y) {
    int r;
    if (x > y) {
        int t = x;
        x = y;
        y = t;
    }

    r = y % x;
    while (r != 0) {
        y = x;
        x = r;
        r = y % x;
    }
    return x;
}

int main(int argc, char *argv[]) {
    int target;
    vector<cargo> cargos;
    vector<int> cost;
    cargo ca;
    cargo *p;
    string line;
    char name[1024];
    int weight, value, g, i, j;

    ifstream input(argv[1]);

    getline(input, line);
    sscanf(line.c_str(), "%d", &target);

    while (getline(input, line)) {
        sscanf(line.c_str(), "%s %d %d", name, &weight, &value);
        ca.name = name;
        ca.weight = weight;
        ca.value = value;
        cargos.push_back(ca);
    }

    g = gcd(target, cargos[0].weight);
    for (i = 1; i < (int)cargos.size(); i++) {
        g = gcd(g, cargos[i].weight);
    }

    target = target / g;
    for (i = 0; i < (int)cargos.size(); i++) {
        p = &cargos[i];
        p->weight /= g;
    }

    cost.resize(target + 1);
    for (i = 0; i <= target; i++)
        cost[i] = INT_MAX;

    int size = cargos.size();
    cost[0] = 0;
    for (i = 0; i <= target; i++) {
        for (j = 0; j < size; j++) {
            p = &cargos[j];
            if (p->weight > i) {
                if (p->value < cost[i]) {
                    cost[i] = p->value;
                }
            } else {
                int w = i - p->weight;
                int sum_value = cost[w] + p->value;
                if (sum_value < cost[i]) {
                    cost[i] = sum_value;
                }
            }
        }
    }

    cout << cost[target] << endl;
}
