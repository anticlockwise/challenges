#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

const int MAXN = 1000000;

struct gene {
    int start;
    int stop;
    int score;

    gene (int s=0, int t=0, int c=0) {
        start = s;
        stop = t;
        score = c;
    }
};

bool compare(const gene &g1, const gene &g2) {
    return g1.stop < g2.stop;
}

int main() {
    int len_dna, ngene;
    char c;
    int i, j, start, stop, score;
    int max_scores[MAXN + 1], parents[MAXN];
    int global_max = 0, global_maxi = -1;
    vector<gene> predictions;
    gene g, *p, *q;

    for (i = 0; i < MAXN; i++) {
        max_scores[i] = 0;
        parents[i] = -1;
    }

    cin >> len_dna;
    for (i = len_dna - 1; i >= 0; i--) {
        cin >> c;
    }

    cin >> ngene;
    for (i = 0; i < ngene; i++) {
        cin >> g.start >> g.stop >> g.score;
        predictions.push_back(g);
    }

    sort(predictions.begin(), predictions.end(), compare);

    for (i = 0; i < ngene; i++) {
        p = &predictions[i];
        for (j = i - 1; j >= 0; j--) {
            q = &predictions[j];
            if (q->stop < p->start) {
                parents[i] = j;
                break;
            }
        }
    }

    max_scores[0] = 0;
    for (i = 1; i < ngene + 1; i++) {
        int inc = predictions[i - 1].score + max_scores[parents[i - 1] + 1];
        int exc = max_scores[i - 1];
        max_scores[i] = ((inc > exc) ? inc : exc);
    }

    cout << max_scores[ngene] << endl;
}
