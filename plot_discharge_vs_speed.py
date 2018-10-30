import sys
import csv
import matplotlib.pyplot as plt
import numpy as np

# program parameters
# [files_base_name] [range_start] [range_end] [range_step] [repetitions]
#
# files_base_name: convention used in file naming ('[speed].[repetition].csv' will be added to files_base_name to obtain the filename)
# range_start: speed range start
# range_end: speed range end
# range_step: separation between different speeds
# repetitions: number of simulation per speed value
#
# example: to plot simulations for speeds ranging from 0.5 to 6.0
# with steps of 0.5, 5 simulations per value and files names like
# out/flowV.{speed}.{repetition}.csv
#  python3 script.py out/flowV 0.5 6.0 0.5 5

base_name = sys.argv[1]
range_start = float(sys.argv[2])
range_end = float(sys.argv[3])
range_step = float(sys.argv[4])
repetitions = int(sys.argv[5])

def frange(start, stop, step):
    i = start
    while i < stop:
        yield i
        i += step

# open csv files
inputs = {}
for speed_value in frange(range_start, range_end+range_step, range_step):
    inputs_for_speed = [];
    for repetition in range(0, repetitions):
      filename = base_name + str(speed_value) + "." + str(repetition+1) + ".csv"
      print("Opening file: " + filename + "...")
      inputs_for_speed.append(open(filename, "r"))
    inputs[str(speed_value)] = inputs_for_speed

# create csv readers
readers = {}
for key in inputs.keys():
    reader_list = []
    for file in inputs[key]:
        reader_list.append(csv.reader(file, delimiter='\t'))
    readers[key] = reader_list;

times = {}

# get evacuation times
for key in readers.keys():
    times_for_speed = []
    for reader in readers[key]:
        last_time = 0.0
        for row in reader:
            last_time = row[0]
        times_for_speed.append(float(last_time))
    times[key] = times_for_speed

# calculate means and errors
speeds = []
means = []
errors = []
for key in times.keys():
    speeds.append(float(key))
    means.append(np.mean(times[key]))
    errors.append(np.std(times[key]))

plt.errorbar(speeds, means, errors, marker='x')

plt.ylabel("Tiempo de evacuación [s]", fontsize=20)
plt.xlabel("Velocidad deseada [m/s]", fontsize=20)

plt.show()
