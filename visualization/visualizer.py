import os
from typing import cast
from matplotlib.collections import PatchCollection
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation


INPUT_FILE = "../data/output.txt"
OUTPUT_FOLDER = "animation"
OUTPUT_FILE_WITHOUT_EXTENSION = "animation"

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
line = list(map(float,f.readline().strip().split(" ")))
system_time_step = line[0]
snaps_time_step = line[1]
line = f.readline()
while line:
    line = line.strip().split(" ")
    if len(line) < 2:
            i+=1
    elif i == 0:
        walls.append([
            (float(line[0]),float(line[1])),
            (float(line[2]),float(line[3]))
        ])
    elif i == 1:
        targets.append([
            (float(line[0]),float(line[1])),
            (float(line[2]),float(line[3]))
        ])
    else:
        snapi = i - 2
        while len(snaps) <= snapi:
            snaps.append([])
        snaps[snapi].append(Particle(float(line[6]),float(line[7]),float(line[3])))
    
    line = f.readline()

def get_circles(particles):
    return [plt.Circle((p.x,p.y),p.r) for p in particles]

def update_circles(i):
    global ax
    global patch_collection
    global snaps
    patch_collection.set_paths(get_circles(snaps[i]))
    ax.set_title("time: {:.2f}".format(i*snaps_time_step))
    return patch_collection

ax = plt.gca()
for w in walls:
    ax.add_line(plt.Line2D((w[0][0],w[1][0]),(w[0][1],w[1][1])))
for t in targets:
    ax.add_line(plt.Line2D((t[0][0],t[1][0]),(t[0][1],t[1][1]), color="red"))
    
patch_collection = PatchCollection(get_circles(snaps[0]))
ax.add_collection(patch_collection)

plt.xlim([-5, 25])
plt.ylim([-15, 25])
plt.gcf().set_size_inches(12,12)
plt.gca().set_aspect("equal","box")

try:
    os.makedirs(OUTPUT_FOLDER)
except:
    print("could make dirs")

WHEN_TO_SAVE = -1
def get_when_to_save(total):
    t = total
    res = 1
    while t > 10:
        res = res * 10
        t = t / 10
    if res != 1:
        res = res / 10
    return res
def progress_callback(curr,total):
    global WHEN_TO_SAVE
    if WHEN_TO_SAVE < 0:
        WHEN_TO_SAVE = get_when_to_save(total)
    if curr % WHEN_TO_SAVE == 0:
        print(f"current frame:{curr}\ttotal:{total}")


ani = FuncAnimation(plt.gcf(),update_circles,frames=len(snaps),interval=1000*snaps_time_step,blit=False)
ani.save(os.path.join(OUTPUT_FOLDER,OUTPUT_FILE_WITHOUT_EXTENSION+".avi"),progress_callback=progress_callback)
ani.save(os.path.join(OUTPUT_FOLDER,OUTPUT_FILE_WITHOUT_EXTENSION+".gif"),progress_callback=progress_callback)


plt.show()