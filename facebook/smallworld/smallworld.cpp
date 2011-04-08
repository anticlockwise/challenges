/*
 * =====================================================================================
 *
 *       Filename:  smallworld.cpp
 *
 *    Description:  Facebook Puzzle - Snack Level: It's A Small World
 *
 *        Version:  1.0
 *        Created:  04/06/2011 20:49:56
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <cfloat>

using namespace std;

struct point {
    int id;
    double x;
    double y;

    point (int n=-1, double i=0.0, double j=0.0) {
        id = n;
        x = i;
        y = j;
    }

    double operator - (const point p) {
        double a = x - p.x;
        double b = y - p.y;
        return a * a + b * b;
    }
};

bool compare_x (const point &p1, const point &p2) {
    return p1.x < p2.x;
}

bool compare_y (const point &p1, const point &p2) {
    return p1.y < p2.y;
}

bool compare_d (const pair<point, double> &d1, const pair<point, double> &d2) {
    return d1.second < d2.second;
}

void build_tree (vector<point> &tree, vector<point> friends, int depth, int index) {
    int len = friends.size();
    if (len == 0)
        return;

    point mid;
    vector<point> left, right;
    int i, m;
    if (depth % 2 == 0) {
        sort(friends.begin(), friends.end(), compare_x);
    } else {
        sort(friends.begin(), friends.end(), compare_y);
    }

    m = len / 2;
    mid = friends[m];
    tree[index] = mid;

    for (i = 0; i < m; i++) {
        left.push_back(friends[i]);
    }
    build_tree (tree, left, depth + 1, index * 2 + 1);

    for (i = m + 1; i < len; i++) {
        right.push_back(friends[i]);
    }
    build_tree (tree, right, depth + 1, index * 2 + 2);
}

void nearest_k (vector<point> tree, int cur_node, point p, int k,
        vector< pair<point, double> > &min, double &k_dist, int depth) {
    int axis = depth % 2;
    double d;
    int near, far;

    if (cur_node < tree.size() && tree[cur_node].id != -1) {
        if (axis == 0) {
            d = p.x - tree[cur_node].x;
        } else {
            d = p.y - tree[cur_node].y;
        }

        near = (d <= 0 ? cur_node*2+1 : cur_node*2+2);
        far  = (d <= 0 ? cur_node*2+2 : cur_node*2+1);

        nearest_k (tree, near, p, k, min, k_dist, depth + 1);

        if (d * d < k_dist)
            nearest_k (tree, far, p, k, min, k_dist, depth + 1);

        d = p - tree[cur_node];
        if (d < k_dist) {
            pair<point, double> pi(tree[cur_node], d);
            min.push_back(pi);
            push_heap(min.begin(), min.end(), compare_d);

            int s = min.size();
            if (s > k) {
                int nremove = s - k;
                int i;
                for (i = 0; i < nremove; i++) {
                    pop_heap(min.begin(), min.end(), compare_d);
                    min.pop_back();
                }

                k_dist = min.front().second;
            }
        }
    }
}

int main(int argc, char *argv[]) {
    vector<point> friends;
    vector<point> tree;
    point p;
    int i, j, nfriends=0, nnodes=1;

    ifstream input(argv[1]);

    while (input >> p.id >> p.x >> p.y) {
        friends.push_back(p);
        nfriends++;
    }

    while (nnodes <= nfriends)
        nnodes *= 2;
    nnodes -= 1;

    tree.resize(nnodes);
    build_tree(tree, friends, 0, 0);

    vector< pair<point, double> > min;
    for (i = 0; i < nfriends; i++) {
        double k_dist = DBL_MAX;
        nearest_k(tree, 0, friends[i], 4, min, k_dist, 0);
        sort(min.begin(), min.end(), compare_d);
        cout << min[0].first.id << " " << min[1].first.id << ","
             << min[2].first.id << "," << min[3].first.id << endl;
        min.clear();
    }
}
