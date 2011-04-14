/*
 * =====================================================================================
 *
 *       Filename:  smallworld.cpp
 *
 *    Description:  Facebook Puzzle It's A Small World: KD-Tree + K-Nearest Neighbour
 *                  Search
 *
 *        Version:  1.0
 *        Created:  04/06/2011 20:49:56
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
#include <vector>
#include <cmath>
#include <algorithm>
#include <cfloat>

using namespace std;

struct point {
    int number;
    double xcoord;
    double ycoord;

    point(int n=0, double x=0, double y=0) {
        number = n;
        xcoord = x;
        ycoord = y;
    }

    double operator [](int axis) {
        if (axis == 0)
            return xcoord;
        else
            return ycoord;
    }
};

typedef pair<double, point> nloc;

struct person {
    point location;
    person *left;
    person *right;

    person(point loc,
            person *l, person *r) {
        location = loc;
        left = l;
        right = r;
    }
};

bool compare_x (const point &p1, const point &p2) {
    return p1.xcoord < p2.xcoord;
}

bool compare_y (const point &p1, const point &p2) {
    return p1.ycoord < p2.ycoord;
}

person *build_tree(vector<point> &coords,
        int depth=0) {
    if (coords.empty()) {
        return NULL;
    }

    int k = 2;
    int axis = depth % k + 1;
    int median = coords.size() / 2;
    person *p = NULL;

    if (axis == 0) {
        sort(coords.begin(), coords.end(), compare_x);
    } else {
        sort(coords.begin(), coords.end(), compare_y);
    }

    int i;
    vector<point> lower_half, higher_half;
    for (i = 0; i < median; i++) {
        lower_half.push_back(coords[i]);
    }
    for (i = median + 1; i < coords.size(); i++) {
        higher_half.push_back(coords[i]);
    }
    p = new person(coords[median],
            build_tree(lower_half, depth+1),
            build_tree(higher_half, depth=1));
    return p;
}

bool compare_d(const nloc &l1, const nloc &l2) {
    return l1.first < l2.first;
}

bool compare_n(const point &p1, const point &p2) {
    return p1.number < p2.number;
}

double distance(point *c1, point *c2) {
    return pow(c1->xcoord - c2->xcoord, 2) + pow(c1->ycoord - c2->ycoord, 2);
}

void add_nearest(vector<nloc> &nearest, person *root, point coord) {
    double d = distance(&(root->location), &coord);
    if (nearest.size() < 4 || d < nearest.back().first) {
        if (nearest.size() >= 4) {
            nearest.pop_back();
        }
        nearest.push_back(make_pair(d, root->location));
        sort(nearest.begin(), nearest.end(), compare_d);
    }
}

void nearest(vector<nloc> &nbs, person *root, point coord, int depth=0) {
    int axis = depth % 2 + 1;

    if (root->left == NULL && root->right == NULL) {
        add_nearest(nbs, root, coord);
        return;
    }

    person *nearer=NULL, *further=NULL;
    if (root->right == NULL || (root->left != NULL && coord[axis] <= root->location[axis])) {
        nearer = root->left;
        further = root->right;
    } else {
        nearer = root->right;
        further = root->left;
    }

    nearest(nbs, nearer, coord, depth+1);

    if (further != NULL) {
        if (nbs.size() < 4 || pow((coord[axis] - root->location[axis]), 2) < nbs.back().first) {
            nearest(nbs, further, coord, depth+1);
        }
    }

    add_nearest(nbs, root, coord);
}

int main(int argc, char *argv[]) {
    ifstream input(argv[1]);
    int number;
    double x, y;
    vector<point> coords;
    point p;
    int i, j;

    while (input >> number >> x >> y) {
        p.number = number;
        p.xcoord = x;
        p.ycoord = y;
        coords.push_back(p);
    }

    person *tree = build_tree(coords);
    sort(coords.begin(), coords.end(), compare_n);
    int size = coords.size();
    vector<nloc> nearest_3;

    for (i = 0; i < size; i++) {
        nearest(nearest_3, tree, coords[i]);
        cout << coords[i].number << " ";
        for (j = 0; j < 3; j++) {
            cout << nearest_3[j+1].second.number;
            if (j != 2) {
                cout << ",";
            }
        }
        cout << endl;
        nearest_3.clear();
    }

    return 0;
}
