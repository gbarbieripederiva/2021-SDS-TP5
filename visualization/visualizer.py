from matplotlib.collections import PatchCollection
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation


INPUT_FILE = "../data/output.txt"

class Particle:
    def __init__(self,x = 0,y = 0,r = 0):
        self.x = x
        self.y = y
        self.r = r







f = open(INPUT_FILE,"r")

walls = []
targets = []

snaps = []

i = 0
line = f.readline()
while line:
    line = line.strip().split(" ")
    if len(line) < 2:
            i+=1
    elif i == 0:
        walls.append([float(line[0]),float(line[1])])
    elif i == 1:
        targets.append([float(line[0]),float(line[1])])
    else:
        snapi = i - 2
        while len(snaps) <= snapi:
            snaps.append([])
        snaps[snapi].append(Particle(float(line[6]),float(line[7]),float(line[3])))
    
    line = f.readline()

def drawSnap(particles):
    global ax
    circles = [plt.Circle((p.x,p.y),p.r) for p in particles]
    col = PatchCollection(circles)
    ax.add_collection(col)
    return ax

ax = plt.gca()

ani = FuncAnimation(ax.figure,drawSnap,snaps,interval=100)

plt.show()