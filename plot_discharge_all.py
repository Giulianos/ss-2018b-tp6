import sys
import csv
import matplotlib.pyplot as plt
import numpy as np

base_name = sys.argv[1]
repetitions = int(sys.argv[2])

# open csv files
inputs = []
for repetition in range(0, repetitions):
  filename = base_name + "." + str(repetition+1) + ".csv"
  print("Opening file: " + filename + "...")
  inputs.append(open(filename, "r"))

# create readers for each file
readers = []
for file in inputs:
  print("Adding new reader...")
  readers.append(csv.reader(file, delimiter='\t'))

# generate array for each file
all_values = []
create = True
for reader in readers:
    values = []
    for row in reader:
        values.append(float(row[0]))
    all_values.append(values)

# plot values
for values in all_values:
    if len(sys.argv) > 3 and sys.argv[3] == 'scatter':
        plt.scatter(range(0, len(values)), values, marker=".")
    else:
        plt.plot(values, marker=".");

plt.xlabel("Cantidad de personas que salieron", fontsize=20)
plt.ylabel("Tiempo [s]", fontsize=20)
plt.show()

# close csv files
for file in inputs:
  file.close()
