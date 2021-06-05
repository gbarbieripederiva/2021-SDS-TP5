import os
import matplotlib.pyplot as plt
import numpy as np

EXPERIMENT_DATA_PATH = os.path.join("..","data","experiment", "experimentC.txt")

def parse(path):
    file = open(path, "r")
    line = file.readline().strip().split("-")
    dt = float(line[1])
    xs = []
    ys = []
    ds = []
    ns = []

    while line:
        line = file.readline()
        title = line.strip().split("-")
        d = float(title[0])
        ds.append(d)
        n = int(title[1])
        ns.append(n)
        line = file.readline()
        x_sim = []
        y_sim = []
        while line!="\n" and line!='':
            temp = line.strip().split(" ")
            x = []
            y = []
            for j in temp:
                aux = j.strip().split("-")
                x.append(float(aux[0]))
                y.append(int(aux[1]))
            x_sim.append(x)
            y_sim.append(y)
            line = file.readline()
        xs.append(x_sim)
        ys.append(y_sim)
    
    return dt, xs, ys, ds, ns


def max_time(times):
    max_t = 0
    for iteration in times:
        for t in iteration:
            last_time = t[-1]
            if last_time > max_t:
                max_t = last_time
    return np.ceil(max_t)


dt, xs, ys, ds, ns = parse(EXPERIMENT_DATA_PATH)
avg_x = np.arange(0, max_time(xs), 3)
avg_y = []
for i, (iteration_x, iteration_y) in enumerate(zip(xs, ys)):
    print(i)
    avg_y.append([])
    for time in avg_x:
        accum_y = 0
        for sim_x, sim_y in zip(iteration_x, iteration_y):
            prev_y = 0
            for x, y in zip(sim_x, sim_y):
                if np.ceil(x) == time:
                    accum_y += y - prev_y
                prev_y = y
        accum_y /= len(iteration_y)
        avg_y[i].append(accum_y)

for i in range(len(avg_y)):
    plt.plot(avg_x, avg_y[i], label=f"N={ns[i]} d={ds[i]}", linewidth=2)

plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Caudal (1/s)', fontsize=16)
plt.legend(fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()