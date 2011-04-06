#include <iostream>
#include <stack>
#include <climits>

using namespace std;

const int MAXROW = 10;
const int MAXCOL = 100;

int main() {
    int nrow, ncol;
    long grid[MAXROW][MAXCOL];
    long dp[MAXROW][MAXCOL];
    long i, j, k, weight;
    int dirs[] = { 1, 0, -1 };

    while (cin >> nrow >> ncol) {
        for (i = 0; i < MAXROW; i++)
            for (j = 0; j < MAXCOL; j++)
                dp[i][j] = LONG_MAX;

        for (i = 0; i < nrow; i++) {
            for (j = 0; j < ncol; j++) {
                cin >> weight;
                grid[i][j] = weight;
            }
        }

        for (i = 0; i < nrow; i++)
            dp[i][0] = grid[i][0];

        for (j = 0; j < ncol - 1; j++) {
            long c = j + 1;
            for (i = 0; i < nrow; i++) {
                for (k = 0; k < 3; k++) {
                    long r = i + dirs[k];
                    if (r < 0)
                        r = nrow - 1;
                    else if (r >= nrow)
                        r = 0;

                    long w = dp[i][j] + grid[r][c];
                    if (dp[r][c] > w) {
                        dp[r][c] = w;
                    }
                }
            }
        }

        stack<long> s;
        long min = LONG_MAX, min_i = -1, final_min;
        for (i = 0; i < nrow; i++) {
            if (dp[i][ncol - 1] < min) {
                min = dp[i][ncol - 1];
                min_i = i;
            }
        }
        final_min = min;
        s.push(min_i + 1);

        for (j = ncol - 2; j >= 0; j--) {
            min = LONG_MAX;
            long mi = -1;
            for (k = 0; k < 3; k++) {
                long r = min_i + dirs[k];
                if (r < 0)
                    r = nrow - 1;
                else if (r >= nrow)
                    r = 0;

                if (dp[r][j] <= min) {
                    if (dp[r][j] == min) {
                        if (r < mi)
                            mi = r;
                    } else {
                        mi = r;
                    }
                    min = dp[r][j];
                }
            }
            min_i = mi;
            s.push(mi + 1);
        }

        while (!s.empty()) {
            long v = s.top();
            s.pop();
            cout << v << " ";
        }
        cout << endl;
        cout << final_min << endl;
    }
}
