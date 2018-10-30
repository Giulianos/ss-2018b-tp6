import sys
import csv
import matplotlib.pyplot as plt
import numpy as np

base_name = sys.argv[1]
repetitions = int(sys.argv[2])
window = int(sys.argv[3])

# Plot flow function
def plot_flow(means, window):
  # calculate dts
  dts = []
  dts.append(means[0])
  for index in range(1, len(means)):
    dts.append(means[index] - means[index-1])

  # calculate flow
  flows = []
  flows_times = []
  window_value = 0
  window_left_index = 0
  window_right_index = window-1
  window_time = 0;

  # Calculate current window accumulated time
  for t in range(0, window):
    window_value += dts[t]

  flows.append(window/window_value)
  window_time = window_value
  flows_times.append(window_time)

  # Slide window
  while window_right_index < len(dts)-1:
    window_value -= dts[window_left_index];
    window_left_index += 1
    window_right_index += 1
    window_value += dts[window_right_index]
    window_time += dts[window_right_index]
    flows_times.append(window_time)
    flows.append(window/window_value)

  plt.plot(flows_times, flows, marker='.')
  pass

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

for i in range(3, len(sys.argv)):
  plot_flow(means, int(sys.argv[i]))

plt.xlabel("Caudal [1/s]", fontsize=20)
plt.ylabel("Tiempo [s]", fontsize=20)

plt.show()

# close csv files
for file in inputs:
  file.close()
