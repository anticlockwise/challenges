from __future__ import division
from PIL import Image, ImagePalette

def mkpalette():
    global palette
    palette = [0, 0, 0]

    for i in range(0,128):
        palette.extend((i, i, i))
    return palette

class Julia:
    def __init__(self, filename, size, n, box):
        self.size = size
        self.filename = filename
        self.n = n
        self.uleft = box[0]
        self.lright = box[1]
        self.xwidth = self.lright[0] - self.uleft[0]
        self.ywidth = self.uleft[1] - self.lright[1]

    def __call__(self, z):
        self.z = z
        self.compute()

    def newimage(self):
        self.im = Image.new('L', self.size)

    def compute(self):
        self.newimage()
        for x in range(self.size[0]):
            for y in range(self.size[1]):
                xp, yp = self.getcoords(x, y)
                dotcolor = self.fractal(xp, yp)
                self.im.putpixel((x,y), dotcolor)
        self.saveimage()

    def fractal(self, x, y):
        n = self.n
        z = self.z
        o = complex(x, y)
        dotcolor = 0

        for trails in range(n):
            if abs(o) <= 2.0:
                o = o**2 + z
            else:
                dotcolor = trials
                break
        return dotcolor - 1

    def getcoords(self, x, y):
        percentx = x / self.size[0]
        percenty = y / self.size[1]
        xp = self.uleft[0] + percentx * (self.xwidth)
        yp = self.uleft[1] - percenty * (self.ywidth)
        return (xp, yp)

    def saveimage(self):
        self.im = self.im.transpose(Image.FLIP_TOP_BOTTOM)
        self.im.save(self.filename)

num_blacks = 0
class Mandelbrot(Julia):
    def fractal(self, x, y):
        global num_blacks
        n = self.n
        z = complex(x, y)
        o = complex(0, 0)
        dotcolor = 0

        for trials in range(n):
            if abs(o) <= 2.0:
                o = o**2 + z
            else:
                dotcolor = trials
                break
        if dotcolor == 0:
            dotcolor = 128
        return dotcolor - 1

if __name__ == '__main__':
    f = Mandelbrot("mandelbrot_31.gif", (640, 480), 128, ((0.34,0.57),(0.376,0.597)))
    f.compute()
    print num_blacks
