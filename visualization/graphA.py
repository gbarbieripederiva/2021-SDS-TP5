import os
import matplotlib.pyplot as plt

EXPERIMENT_DATA_PATH = os.path.join("..","data","experiment", "experimentA.txt")

def parse(path):
    file = open(path, "r")
    line = file.readline().strip().split(" ")
    time_step = float(line[1])
    line = file.readline()
    xs = []
    ys = []
    while line:
        temp = list(map(float,line.strip().split(" ")))
        t = 0
        x = []
        y = []
        particles = 0
        while t < temp[-1] + time_step:
            if temp[particles] < t:
                particles+=1
            x.append(t)
            y.append(particles)
            t += time_step
        xs.append(x)
        ys.append(y)
        line = file.readline()
    return xs,ys


xs,ys = parse(EXPERIMENT_DATA_PATH)

for i,_ in enumerate(xs):
    plt.plot(xs[i], ys[i], label=f"Intento {i}", linewidth=2)
plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Particulas Egresadas', fontsize=16)
plt.legend(fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()