#include <iostream>

using namespace std;

const int MAXN = 3650;

int main() {
    int ncases, ndays, nparties;
    bool table[MAXN + 1];
    int i, j;

    cin >> ncases;

    for (i = 0; i < ncases; i++) {
        int party, step, hartals = 0;

        cin >> ndays >> nparties;

        for (j = 0; j <= ndays; j++)
            table[j] = false;

        for (j = 0; j < nparties; j++) {
            cin >> party;
            step = party;
            while (party <= ndays) {
                if (party % 7 != 6 && party % 7 != 0) {
                    table[party] = true;
                }
                party += step;
            }
        }

        for (j = 1; j <= ndays; j++) {
            if (table[j])
                hartals++;
        }
        cout << hartals << endl;
    }
}
