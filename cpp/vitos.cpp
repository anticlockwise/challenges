#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int ncases, nfamily;
    int i, j;
    int street_no;
    vector<int> streets;

    cin >> ncases;

    for (i = 0; i < ncases; i++) {
        cin >> nfamily;
        for (j = 0; j < nfamily; j++) {
            cin >> street_no;
            streets.push_back(street_no);
        }

        sort(streets.begin(), streets.end());

        if (nfamily % 2 == 0) {
            nfamily --;
        }

        cout << streets[nfamily / 2] << endl;

        streets.clear();
    }
}
