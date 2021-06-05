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

caudales = []
radios = []
errores = []
for i in range(len(avg_y)):
    caudales.append(np.mean(avg_y[i]))
    errores.append(np.std(avg_y[i]))

for i, radius_iteration in enumerate(rs):
    radios.append([])
    for sim_r in radius_iteration:
        radios[i].append(np.mean(sim_r))

avg_r = []
for rad in radios:
    avg_r.append(np.mean(rad))

print(avg_r)

plt.scatter(ds, caudales)
plt.errorbar(ds, caudales, yerr=errores, ecolor='gray', capsize=2)
plt.xticks(np.arange(min(ds), max(ds) + 0.1, 0.6))
plt.yticks(np.arange(0, max(caudales) + 1, 1))
plt.xlabel('Tamaño de salida (m)', fontsize=18)
plt.ylabel('Caudal (1/s)', fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()

# for i in range(len(avg_y)):
#     plt.plot(avg_x, avg_y[i], label=f"N={ns[i]} d={ds[i]}", linewidth=2)
# plt.xlabel('Tiempo (s)', fontsize=18)
# plt.ylabel('Caudal (1/s)', fontsize=16)
# plt.legend(fontsize=16)
# plt.gcf().set_size_inches(16,12)
# plt.show()

caudales_teoricos = []
for d, r in zip(ds, avg_r):
    caudales_teoricos.append((d-0.5*r) ** 1.5)

precision = 0.00001
Bs = np.arange(0, 1.25, precision)
erroresB = []
minError = -1
minB = 0
for B in Bs:
    accum = 0
    for y, fx in zip(caudales, caudales_teoricos):
        accum += (y - B*fx) ** 2
    erroresB.append(accum)
    if minError == -1 or accum < minError:
        minError = accum
        minB = B

print("B:",minB)
print("Error minimo:", minError)

plt.plot(Bs, erroresB)
plt.xlabel('B', fontsize=18)
plt.ylabel('Error', fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()
