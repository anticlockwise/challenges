#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct base {
    int force;
    int minerals;
    int index;

    base (int i=-1, int f=0, int m=0) {
        index = i;
        force = f;
        minerals = m;
    }
};

int main() {
    int nplanets, nbases, nzergs;
    int i, j;
    base b;

    cin >> nplanets;

    for (i = 0; i < nplanets; i++) {
        vector<base> bases;

        cin >> nbases >> nzergs;
        for (j = 0; j < nbases; j++) {
            b.index = j;
            cin >> b.force >> b.minerals;
            bases.push_back(b);
        }
    }
}
