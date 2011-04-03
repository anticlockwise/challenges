/*
 * =====================================================================================
 *
 *       Filename:  minesweeper.cpp
 *
 *    Description:  UVa 10189 - Minesweeper
 *
 *        Version:  1.0
 *        Created:  04/03/2011 19:02:59
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */

#include <iostream>

using namespace std;

#define MAXN 100

int directions[8][2] = {
    { -1, -1 },
    { 0, -1 },
    { 1, -1 },
    { -1, 0 },
    { 1, 0 },
    { -1, 1 },
    { 0, 1 },
    { 1, 1 }
};

int field[MAXN][MAXN];

void add_mine(int x, int y) {
    int i;
    for (i = 0; i < 8; i++) {
        int x1 = x + directions[i][0];
        int y1 = y + directions[i][1];
        if (x1 >= 0 && y1 >= 0 && x1 < MAXN && y1 < MAXN) {
            if (field[x1][y1] != -1) {
                field[x1][y1] ++;
            }
        }
    }
}

int main() {
    int n, m, fn = 1;
    int i, j;
    char c;

    while (cin >> n >> m) {
        if (n == 0 && m == 0)
            break;

        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                field[i][j] = 0;
            }
        }

        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                cin >> c;
                if (c == '*') {
                    field[i][j] = -1;
                    add_mine(i, j);
                }
            }
        }

        cout << "Field #" << fn++ << endl;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if (field[i][j] == -1)
                    cout << '*';
                else
                    cout << field[i][j];
            }
            cout << endl;
        }
        cout << endl;
    }
}
