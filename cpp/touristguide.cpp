#include <iostream>
#include <cmath>

using namespace std;

int main() {
    const int MAXN = 100;
    int ncities, nroads;
    int dp[MAXN][MAXN];
    int i, j, k, from, to, capacity, n = 1;

    while (cin >> ncities >> nroads) {
        if (ncities == 0 && nroads == 0)
            break;

        for (i = 0; i < MAXN; i++)
            for (j = 0; j < MAXN; j++)
                dp[i][j] = 0;

        for (i = 0; i < nroads; i++) {
            cin >> from >> to >> capacity;
            dp[from - 1][to - 1] = capacity;
            dp[to - 1][from - 1] = capacity;
        }

        for (i = 0; i < ncities; i++) {
            for (j = 0; j < ncities; j++) {
                for (k = 0; k < ncities; k++) {
                    dp[j][k] = max(dp[j][k],
                            min(dp[j][i], dp[i][k]));
                }
            }
        }

        cin >> from >> to >> capacity;
        int max = dp[from - 1][to - 1];
        int ntrips = ceil((float)capacity / max);
        cout << "Senario #" << n++ << endl;
        cout << "Minimum Number of Trips = " << ntrips << endl;
        cout << endl;
    }
}
