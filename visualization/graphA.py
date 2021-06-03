import os
import numpy as np
import matplotlib.pyplot as plt

EXPERIMENT_DATA_PATH = os.path.join("data/experiment", "experimentA.txt")

def parse(path):
    file = open(path, "r")
    file.readline()
    line = file.readline()
    while line:
        temp = list(map(float,line.strip().split(" ")))

        line = file.readline()
    return


x,x = parse(EXPERIMENT_DATA_PATH)

plt.plot(time, approximation, label=methods[i], linewidth=2)
plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Particulas Egresadas', fontsize=16)
plt.legend(fontsize=16)
plt.show()