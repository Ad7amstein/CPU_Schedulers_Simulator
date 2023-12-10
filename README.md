# CPU_Schedulers_Simulator
Scheduling  is  a  fundamental operating-system  function. 
Almost  all computer  resources  are scheduled before use.
The CPU is, of course, one of the primary computer resources.
Thus, its  scheduling  is  central  to  operating-system  design.
CPU  scheduling  determines  which processes  run  when  there  are  multiple  run-able  processes.
CPU scheduling  is  important because  it can have a  big effect on resource utilization and the overall performance of the system.

## Schedulers
1. Non-Preemptive  Shortest-Job First (SJF) (using context switching).
2. Shortest-Remaining Time First (SRTF) Scheduling  (with the solving of starvation problem using any way can be executed correctly).
3. Non-preemptive  Priority Scheduling (with the solving of starvation problem using any way can be executed correctly).
4. AG Scheduling

## Requirements
- [Java](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-20-04)

## Quickstart
1. Clone this repo
```sh
git clone https://github.com/Ad7amstein/CPU_Schedulers_Simulator.git
```

2. Compile
```sh
javac *.java
```

3. Run
```sh
java Main
```