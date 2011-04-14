/*
 * =====================================================================================
 *
 *       Filename:  breathalyzer.cpp
 *
 *    Description:  Facebook Puzzles Breathalyzer - Levenshtein Distance + Pruning
 *
 *        Version:  1.0
 *        Created:  04/11/2011 20:23:49
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
#include <string>
#include <map>
#include <algorithm>
#include <vector>
#include <climits>
#include <cmath>

using namespace std;

const int MAXLEN = 100;

inline int edit_distance(string s, string t, int len_s, int len_t, int min_dist) {
    if (abs(len_s - len_t) >= min_dist)
        return min_dist;

    int i, j, k;
    int opt[3];
    int d[MAXLEN][MAXLEN];

    for (i = 0; i < MAXLEN; i++) {
        d[0][i] = i;
        d[i][0] = i;
    }

    for (i = 1; i <= len_s; i++) {
        char si = s[i-1];
        for (j = 1; j <= len_t; j++) {
            char tj = t[j-1];
            opt[0] = d[i-1][j-1] + (si == tj ? 0 : 1);
            opt[1] = d[i][j-1] + 1;
            opt[2] = d[i-1][j] + 1;

            d[i][j] = opt[0];
            for (k = 1; k <= 2; k++) {
                if (opt[k] < d[i][j]) {
                    d[i][j] = opt[k];
                }
            }
        }
    }

    return d[len_s][len_t];
}

int main(int argc, char *argv[]) {
    int total_distance = 0;
    map<string, int> dictionary;
    map<string, int>::iterator it;
    ifstream words("/var/tmp/twl06.txt");
    string word, min_word;
    while (words >> word) {
        transform(word.begin(), word.end(),
                word.begin(), ::tolower);
        dictionary[word] = word.size();
    }

    ifstream input(argv[1]);
    while (input >> word) {
        int min_dist = INT_MAX;
        int len_s = word.size();
        transform(word.begin(), word.end(),
                word.begin(), ::tolower);

        if (dictionary.find(word) != dictionary.end())
            continue;

        for (it = dictionary.begin(); it != dictionary.end(); ++it) {
            int dist = edit_distance(word, it->first, len_s, it->second, min_dist);
            if (dist == 1) {
                min_dist = dist;
                min_word = it->first;
                break;
            }
            if (dist < min_dist) {
                min_dist = dist;
                min_word = it->first;
            }
        }
        total_distance += min_dist;
    }

    cout << total_distance << endl;

    return 0;
}
