/*
 * =====================================================================================
 *
 *       Filename:  3nplus1.cpp
 *
 *    Description:  UVa 100 - 3n + 1 Problem
 *
 *        Version:  1.0
 *        Created:  04/03/2011 13:36:22
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

int main() {
    long l, r;
    long n, k;

    while (cin >> l >> r) {
        int max = 0;
        for (n = l; n <= r; n++) {
            k = n;
            int l_cycle = 1;
            while (k != 1) {
                k = ((k % 2 == 0) ? (k / 2) : (k * 3 + 1));
                l_cycle ++;
            }
            if (l_cycle > max)
                max = l_cycle;
        }

        cout << l << " " << r << " " << max << endl;
    }

    return 0;
}
