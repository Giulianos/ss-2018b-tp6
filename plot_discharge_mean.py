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
  i = 0
  for row in reader:
    if create:
      all_values.append([float(row[0])])
    else:
      all_values[i].append(float(row[0]))
    i+=1
  if create:
      create = False

# calculate mean
means = []
for row in all_values:
  means.append(np.mean(row))

# calculate errors
errors = []
for row in all_values:
  errors.append(np.std(row))

if len(sys.argv) > 3 and sys.argv[3] == 'scatter':
  plt.scatter(range(0, len(means)), values, marker='.')
else:
  plt.errorbar(range(0, len(means)), means, errors, marker='.')

plt.xlabel("Cantidad de personas que salieron", fontsize=20)
plt.ylabel("Tiempo [s]", fontsize=20)

plt.show()

# close csv files
for file in inputs:
  file.close()
