#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
File: level24.py
Author: rshen <anticlockwise5@gmail.com>
Description: The Python Challenge Level 24
'''

import Image
import Tkinter
import array

class PixelSetter:
    # Update display every TK_INTERVAL pixel changes.
    # Larger runs faster but is jerkier.
    def __init__(self, tk, image, TK_INTERVAL=1):
        self.tk = tk
        self.image = image
        self.TK_INTERVAL = TK_INTERVAL
        self.count = 0

    def put(self, x, y, color):
        if self.tk:
            self.image.put(color, (x, y))
            self.count += 1
            if self.count >= self.TK_INTERVAL:
                self.tk.update()
                self.count = 0

# Stack based solution to finding a path through the maze.
def find_path(maze, row, col):
    PATH, WALL, CRUMB = range(3)
    if maze[row][col] == WALL:
        return
    max_row = len(maze) - 1
    max_col = len(maze[0]) - 1
    move = (( 0,  1), # East
            ( 1,  0), # South
            ( 0, -1), # West
            (-1,  0), # North
           )
    stack = [(col, row, 0)]
    while stack:
        col, row, direction = stack.pop()
        if maze[row][col] == PATH:
            # First time we've visited this pixel.
            setpixel(col, row, "#FF0000")
            maze[row][col] = CRUMB
            if row == max_row:
                stack.append((col, row, direction))
                break
        for direction in range(direction, 4):
            drow, dcol = move[direction]
            next_row, next_col = row + drow, col + dcol
            if (0 <= next_row <= max_row and
                  0 <= next_col <= max_col and
                  maze[next_row][next_col] == PATH):
                stack.append((col, row, direction + 1))
                stack.append((next_col, next_row, 0))
                break
        else:
            setpixel(col, row, "#7FFFFF")
    return [p[:2] for p in stack]

# Transform maze.png into maze[row][col] where True == wall,
# False == path (open).
im = Image.open("maze.png")
data = list(im.getdata())
white, black = (255, 255, 255, 255), (0, 0, 0, 255)
wall = set([white, (127, 127, 127, 255)])
width, height = im.size
maze = []
for i in xrange(0, len(data), width):
    maze.append([pixel in wall
                 for pixel in data[i:i+width]]) 

# Tk goodness.  Set tk = None to disable.
tk = Tkinter.Tk()
if tk:
    canvas = Tkinter.Canvas(tk, width=width+10,
                                height=height+10)
    # Save as a .gif so Tk can load it in an eyeblink.
    # Change path to black for greater contrast.
    data = [pixel in wall and white or black
            for pixel in data]
    im2 = im.copy()
    im2.putdata(data)
    im2.save("maze.gif")
    # PhotoImage supports fiddling individual pixels
    # efficiently.
    image = Tkinter.PhotoImage(file="maze.gif")
    canvas.create_image(5, 5, image=image, anchor=Tkinter.NW)
    canvas.pack()
    tk.update()
    setpixel = PixelSetter(tk, image).put
else:
    def setpixel(*args):
        pass

path = find_path(maze, 0, 639)
data = [im.getpixel(coords)[0] for coords in path]
a = array.array('B', data[1::2])
f = open("maze.zip", "wb")
a.tofile(f)
f.close()

if tk:
    tk.mainloop()