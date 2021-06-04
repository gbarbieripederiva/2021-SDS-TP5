import os
import matplotlib.pyplot as plt

EXPERIMENT_DATA_PATH = os.path.join("data","experiment", "experimentC.txt")

def parse(path):
    file = open(path, "r")
    line = file.readline().strip().split(" ")
    xs = []
    ys = []
    x = []
    y = []
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
        while line!="\n" and line!='':
            temp = line.strip().split(" ")
            for j in temp:
                aux = j.strip().split("-")
                x.append(float(aux[0]))
                y.append(int(aux[1]))
            xs.append(x)
            ys.append(y)
            line = file.readline()
    
    return xs,ys,ds,ns


xs,ys,ds,ns = parse(EXPERIMENT_DATA_PATH)



for i in range(len(xs)):
    plt.plot(ys[i],xs[i], color='r' ,label=f"Intento {i}", linewidth=2)    

plt.xlabel('Tiempo (s)', fontsize=18)
plt.ylabel('Particulas Egresadas', fontsize=16)
plt.legend(fontsize=16)
plt.gcf().set_size_inches(16,12)
plt.show()