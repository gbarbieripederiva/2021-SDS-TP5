import os
import matplotlib.pyplot as plt
import numpy as np

EXPERIMENT_DATA_PATH = os.path.join("..","data","experiment", "experimentCD.txt")

def parse(path):
    file = open(path, "r")
    line = file.readline().strip().split("-")
    dt = float(line[1])
    xs = []
    ys = []
    rs = []
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
        r_sim = []
        while line!="\n" and line!='':
            temp = line.strip().split(" ")
            x = []
            y = []
            r = []
            for j in temp:
                aux = j.strip().split("-")
                x.append(float(aux[0]))
                y.append(int(aux[1]))
                r.append(float(aux[2]))
            x_sim.append(x)
            y_sim.append(y)
            r_sim.append(r)
            line = file.readline()
        xs.append(x_sim)
        ys.append(y_sim)
        rs.append(r_sim)
    
    return dt, xs, ys, rs, ds, ns


def max_time(times):
    max_t = 0
    for iteration in times:
        for t in iteration:
            last_time = t[-1]
            if last_time > max_t:
                max_t = last_time
    return np.ceil(max_t)


dt, xs, ys, rs, ds, ns = parse(EXPERIMENT_DATA_PATH)
start_second = 10
end_second = 80
start_y = []
time_step = 1
start_index = int(start_second/dt)
end_index = int(end_second/dt)

for i in range(len(xs)):
    start_y.append([])
    for j in range(len(xs[i])):
        xs[i][j] = xs[i][j][start_index:end_index]
        ys[i][j] = ys[i][j][start_index:end_index]
        rs[i][j] = rs[i][j][start_index:end_index]
        start_y[i].append(ys[i][j][0])

times = np.arange(start_second + 1, end_second + 0.01, time_step)
avg_y = []
for nd_number, (same_nd_x, same_nd_y) in enumerate(zip(xs, ys)):
    print(nd_number)
    avg_y.append([])
    for time in times:
        caudal = 0
        for realization_number, (same_realization_x, same_realization_y) in enumerate(zip(same_nd_x, same_nd_y)):
            prev_y = start_y[nd_number][realization_number]
            for x, y in zip(same_realization_x, same_realization_y):
                if np.ceil(x) == time:
                    caudal += y - prev_y
                prev_y = y
        caudal /= time_step * len(same_nd_y)
        avg_y[nd_number].append(caudal)

caudales = np.mean(avg_y, 1)
errores = np.std(avg_y, 1)

avg_r = np.mean(rs)

plt.scatter(ds, caudales)
plt.errorbar(ds, caudales, yerr=errores, ecolor='gray', capsize=2)
plt.xticks(np.arange(min(ds), max(ds) + 0.1, 0.6), fontsize=16)
plt.yticks(np.arange(0, max(caudales) + 1, 1), fontsize=16)
plt.xlabel('Tamaño de salida (m)', fontsize=18)
plt.ylabel('Caudal (1/s)', fontsize=16)
plt.gcf().set_size_inches(16, 12)
plt.show()


precision = 0.001
Bs = np.arange(0, 2, precision)
c = 0.5
erroresB = []
minError = -1
minB = 0
for B in Bs:
    accum = 0
    for y, d, n in zip(caudales, ds, ns):
        accum += (y - (B*(d-c*avg_r) ** 1.5)) ** 2
    erroresB.append(accum)
    if minError == -1 or accum < minError:
        minError = accum
        minB = B

print("B:", minB)
print("Error minimo:", minError)

plt.plot(Bs, erroresB)
plt.scatter([minB], [minError])
plt.annotate("({:.3f}, {:.3f})".format(minB, minError), (minB, minError + 4),
             horizontalalignment='center', verticalalignment='top', fontsize=16)

plt.xlabel('B', fontsize=18)
plt.ylabel('Error', fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.xticks(fontsize=14)
plt.yticks(fontsize=14)
plt.show()

adjusted_caudal = []
interest_points = []

ds_2 = np.arange(1.2, 3.0, 0.6 / 100)
for d in ds_2:
    adjusted_caudal.append(minB * ((d - c * avg_r) ** 1.5))

for d in ds:
    interest_points.append(minB * ((d-c*avg_r) ** 1.5))

plt.scatter(ds, caudales)
plt.errorbar(ds, caudales, yerr=errores, ecolor='gray', capsize=2, label='Valores medidos')
plt.scatter(ds, interest_points)
plt.plot(ds_2, adjusted_caudal, label='Ley de Beverloo')
plt.xticks(np.arange(min(ds), max(ds) + 0.1, 0.6), fontsize=14)
plt.yticks(np.arange(0, max(caudales) + 1, 1), fontsize=14)
plt.xlabel('Tamaño de salida (m)', fontsize=18)
plt.ylabel('Caudal (1/s)', fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.legend(fontsize=16)
plt.show()