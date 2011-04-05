#include <iostream>

using namespace std;

const int MAXD   = 100;
const int NCARDS = 52;

void print_deck(int deck[NCARDS]) {
    int i, c, s;
    for (i = 0; i < NCARDS; i++) {
        c = deck[i] % 13;
        s = (deck[i] - 1) / 13;
        switch (c) {
            case 1:
                cout << "Ace of ";
                break;
            case 11:
                cout << "Jack of ";
                break;
            case 12:
                cout << "Queen of ";
                break;
            case 0:
                cout << "King of ";
                break;
            default:
                cout << c << " of ";
                break;
        }

        switch (s) {
            case 0:
                cout << "Clubs";
                break;
            case 1:
                cout << "Diamonds";
                break;
            case 2:
                cout << "Hearts";
                break;
            default:
                cout << "Spades";
                break;
        }
        cout << endl;
    }
}

int main() {
    int ncases, ndeals;
    int deck[NCARDS];
    int tmp[NCARDS];
    int deals[MAXD][NCARDS];
    int nswap, curswap, curdeal;
    int i, j, k;

    cin >> ncases;

    for (i = 0; i < ncases; i++) {
        nswap = 0;

        // Initialize the deck
        for (j = 0; j < 4; j++) {
            for (k = 0; k < 12; k++) {
                deck[j * 13 + k] = j * 13 + k + 2;
            }
            deck[j * 13 + 12] = j * 13 + 1;
        }

        cin >> ndeals;
        for (j = 0; j < ndeals; j++) {
            for (k = 0; k < NCARDS; k++) {
                cin >> curswap;
                deals[j][k] = curswap;
            }
        }

        for (j = 0; j < ndeals; j++) {
            cin >> curdeal;
            for (k = 0; k < NCARDS; k++) {
                if (nswap % 2 == 0) {
                    tmp[k] = deck[deals[curdeal - 1][k] - 1];
                } else {
                    deck[k] = tmp[deals[curdeal - 1][k] - 1];
                }
            }
            nswap++;
        }

        if (nswap % 2 == 0) {
            print_deck(deck);
        } else {
            print_deck(tmp);
        }
    }
}
