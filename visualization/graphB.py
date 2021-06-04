import os
import matplotlib.pyplot as plt

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

y = []
for i in range(len(x)):
    y.append(0)
    for times in ys:
        y[i]+=times[i]
    y[i] = y[i] / len(ys)

for i,_ in enumerate(ys):
    plt.plot(y,x, label=f"Intento {i}", linewidth=2)

plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Particulas Egresadas', fontsize=16)
plt.legend(fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()