import os
import matplotlib.pyplot as plt
import numpy as np

EXPERIMENT_DATA_PATH = os.path.join("..","data","experiment", "experimentAB.txt")

def parse(path):
    file = open(path, "r")
    line = file.readline().strip().split(" ")
    line = file.readline()
    ys = []
    while line:
        temp = list(map(float,line.strip().split(" ")))
        ys.append(temp)
        line = file.readline()
    return list(range(1,len(ys[0])+1)),ys


x,ys = parse(EXPERIMENT_DATA_PATH)
mat = np.matrix(ys)
dev = mat.std(0)
y = np.array(mat.mean(0).transpose()).flatten()
errores = np.array(mat.std(0)).flatten()

plt.plot(y, x, linewidth=2, color='b')
# plt.scatter(y, x)
plt.errorbar(y, x, xerr=errores, ecolor='gray', capsize=2)

plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Particulas Egresadas', fontsize=16)
plt.legend(fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()