#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level32.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 32
'''

import sys
sys.setrecursionlimit(
        sys.getrecursionlimit()*6)

def cross(rows, cols):
    return [a+"-"+b for a in rows for b in cols]

def first(func, seq):
    for s in seq:
        if func(s):
            return s
    return seq[-1]

# Horizontal
VERTICAL = [[5],
        [5],
        [5],
        [3, 1],
        [3, 1],
        [5],
        [5],
        [6],
        [5, 6],
        [9, 5],
        [11, 5, 1],
        [13, 6, 1],
        [14, 6, 1],
        [7, 12, 1],
        [6, 1, 11, 1],
        [3, 1, 1, 1, 9, 1],
        [3, 4, 10],
        [8, 1, 1, 2, 8, 1],
        [10, 1, 1, 1, 7, 1],
        [10, 4, 1, 1, 7, 1],
        [3, 2, 5, 2, 1, 2, 6, 2],
        [3, 2, 4, 2, 1, 1, 4, 1],
        [2, 6, 3, 1, 1, 1, 1, 1],
        [12, 3, 1, 2, 1, 1, 1],
        [3, 2, 7, 3, 1, 2, 1, 2],
        [2, 6, 3, 1, 1, 1, 1],
        [12, 3, 1, 5],
        [6, 3, 1],
        [6, 4, 1],
        [5, 4],
        [4, 1, 1],
        [5]]

# Vertical
HORIZONTAL = [[3, 2],
        [8],
        [10],
        [3, 1, 1],
        [5, 2, 1],
        [5, 2, 1],
        [4, 1, 1],
        [15],
        [19],
        [6, 14],
        [6, 1, 12],
        [6, 1, 10],
        [7, 2, 1, 8],
        [6, 1, 1, 2, 1, 1, 1, 1],
        [5, 1, 4, 1],
        [5, 4, 1, 4, 1, 1, 1],
        [5, 1, 1, 8],
        [5, 2, 1, 8],
        [6, 1, 2, 1, 3],
        [6, 3, 2, 1],
        [6, 1, 5],
        [1, 6, 3],
        [2, 7, 2],
        [3, 3, 10, 4],
        [9, 12, 1],
        [22, 1],
        [21, 4],
        [1, 17, 1],
        [2, 8, 5, 1],
        [2, 2, 4],
        [5, 2, 1, 1],
        [5]]

diag = [str(i + 1) for i in range(32)]
constraints = {}
for r in diag:
    for c in diag:
        row, col = int(r)-1, int(c)-1
        constraints[r+"-"+c] = [VERTICAL[col], HORIZONTAL[row]]
choices = '01'
squares = cross(diag, diag)
moves = []
for i in range(1, 33):
    for j in range(1, i+1):
        moves.append(str(j) + "-" + str(i))
        if j != i:
            moves.append(str(i) + "-" + str(j))

#print squares
values = dict((s, 0) for s in squares)
unitlist = ([cross(diag, [c]) for c in diag] +
            [cross([r], diag) for r in diag])
units = dict((s, [u for u in unitlist if s in u]) for s in squares)
peers = dict((s, set(s2 for u in units[s] for s2 in u if s2 != s))
            for s in squares)

def search(values):
    if values is False:
        return False
    if all(len(values[s]) == 1 for s in squares):
        return values # Solved!
    s = [s for s in squares if len(values[s]) > 1][0]
    #printboard(values)
    return some(search(assign(values.copy(), s, d))
                for d in values[s])

def check(values, cons):
    group, i, prev = [], 0, '0'
    for v in values:
        if v in ['0', '01'] and prev == '1':
            group.append(i)
            i = 0
        elif v == '1':
            i += 1
        prev = v
    if i != 0:
        group.append(i)

    last_square, prev = '', ''
    copyv = values[:]
    copyv.reverse()
    for v in copyv:
        if v == '1':
            last_square = prev
            break
        prev = v

    rest_squares = [s for s in values if s == '01']

    if all(len(v) == 1 for v in values):
        if len(group) != len(cons):
            return False
        if len(group) == 0:
            return True
        else:
            for i in range(len(group)):
                if group[i] != cons[i]:
                    return False
            return True

    if len(group) > len(cons):
        return False
    else:
        for i in range(len(group)):
            if group[i] > cons[i]:
                return False
            elif i < (len(group) - 1) and (group[i] != cons[i]):
                return False
            elif i == (len(group) - 1) and (group[i] < cons[i]):
                if last_square == '0':
                    return False
        if len(rest_squares) < ((sum(cons) - sum(group)) + (len(cons) - len(group))):
            return False
        return True

def assign(values, s, d):
    if all(eliminate(values, s, d2) for d2 in values[s] if d2 != d):
        return values
    else:
        return False

def eliminate(values, s, d):
    if d not in values[s]:
        return values # Already eliminated
    values[s] = values[s].replace(d, '')
    if len(values[s]) == 0:
        return False
    hvalues = [values[s2] for s2 in units[s][1]]
    vvalues = [values[s2] for s2 in units[s][0]]
    if not check(hvalues, constraints[s][1]) or\
       not check(vvalues, constraints[s][0]):
        return False

    return values

def parse_grid(grid):
    grid = [c for c in grid if c in '.01']
    values = dict((s, choices) for s in squares)
    for s, d in zip(squares, grid):
        if d in choices and not assign(values, s, d):
            return False
    return values

def printboard(values):
    for r in diag:
        print ''.join(values[r+"-"+c].center(2) for c in diag)
    print
    return values

def all(seq):
    for e in seq:
        if not e: return False
    return True

def some(seq):
    for e in seq:
        if e: return e
    return False

printboard(search(parse_grid(['.' for i in range(32*32)])))
