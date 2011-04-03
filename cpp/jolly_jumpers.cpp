/*
 * =====================================================================================
 *
 *       Filename:  jolly_jumpers.cpp
 *
 *    Description:  UVa 10038 - Jolly Jumpers
 *
 *        Version:  1.0
 *        Created:  04/03/2011 19:31:54
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Rongzhou Shen (rshen), anticlockwise5@gmail.com
 *        Company:  
 *
 * =====================================================================================
 */

#include <iostream>
#include <cmath>

using namespace std;

const int MAXN = 3000;

int main() {
    bool check[MAXN];
    int prev_digit = 3002, cur_digit;
    int num_digits, i;

    while (cin >> num_digits) {
        for (i = 0; i < num_digits; i++) {
            cin >> cur_digit;
            if (prev_digit != 3002) {
                check[abs(cur_digit - prev_digit)] = true;
            }
            prev_digit = cur_digit;
        }

        bool jolly = true;
        for (i = 1; i <= num_digits - 1; i++) {
            jolly = jolly && check[i];
        }
        cout << (jolly ? "Jolly" : "Not jolly") << endl;
    }
}
