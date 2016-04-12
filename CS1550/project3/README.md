#Project3: Virtual Memory Simulator

The goal of this project was to compare the results of several different algorithms on traces of memory references. While simulating an algorithm, statistics are collected about its performance such as the number of page faults that occur and the number of dirty frames that had to be written back to disk.<br>

The implemented algorithms for this project are:<br>
**Opt** – Simulate what the optimal page replacement algorithm would choose if it had perfect knowledge.<br>
**Clock** – Do the circular queue improvement to the second chance algorithm.<br>
**NRU** – Pick a not recently used page using the R and D bits.<br>

The page table is for a 32-bit address space. All pages are be 4KB in size. The number of frames will be a parameter to the execution of the program.

```
To run from the command-line choose one of these options:
java vmsim –n <numframes> -a opt gcc.trace
java vmsim –n <numframes> -a clock gcc.trace
java vmsim –n <numframes> -a nru -r <refresh> gcc.trace
```
![alt tag](https://raw.githubusercontent.com/abrahamjj/CS1550/master/project3/statistics/page_faults_gcc_trace.png)
![alt tag](https://raw.githubusercontent.com/abrahamjj/CS1550/master/project3/statistics/writes_to_disk_gcc_trace.png)

