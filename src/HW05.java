//
//
//  Written By Austin Gailey, Date: 4 Oct 2019
//
//
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.LinkedList;

public class HW05 {
    //Declaring Global Variables
    private static LinkedList<Process> processInputList = new LinkedList<Process>();

    public static void main(String[] args) {
        // write your code here
        initializeOutputFile();
        header();
        readInput();
        //sop("----------------Processes Read From File:---------------------------");
        writeOutput("----------------Processes Read From File:---------------------------");
        printProcesses(processInputList);
        //sop("-----------------End of Processes Read From File--------------------\n");
        writeOutput("-----------------End of Processes Read From File--------------------\n");
        npSJF();        //Run non-preemptive SJF Scheduling
        npPriority();   //Run non-preemptive Priority Scheduling
        roundRobin();   //Run Round Robin Scheduling
    }

    private static void roundRobin(){
        //sop("--------Scheduling results of Round Robin---------------------------");
        writeOutput("--------Scheduling results of Round Robin---------------------------");
        LinkedList<Process> processes = copyProcesses(processInputList);
        LinkedList<Process> processQueue = new LinkedList<Process>();
        int timeQuantum = 10;
        long tMax = 0;
        long t=0;
        int currentProcessIndex = 0;
        for(Process p: processes){  //Gets total burst time of processes
            tMax+= p.getCpuBurst();
        }
        boolean running = true;
        while(running){
            for(int i=0;i<processes.size();i++){
                if(processes.get(i).getArrivalTime() == t){
                    processQueue.add(processes.get(i));
                }
                if(t==0 && !processQueue.isEmpty() && i == 0){
                    //sop("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                    writeOutput("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                }
            }//Add process when the arrival time appears
            if(!processQueue.isEmpty()){
                if(processQueue.get(currentProcessIndex).getCpuBurst() == 0) {
                    //sop("---At time " + t + "ms, CPU finished process " + processQueue.get(currentProcessIndex).getProcessID() + "---");
                    writeOutput("---At time " + t + "ms, CPU finished process " + processQueue.get(currentProcessIndex).getProcessID() + "---");
                    processQueue.remove(currentProcessIndex);
                    currentProcessIndex--;
                    if(currentProcessIndex<0){
                        currentProcessIndex = 0;
                    }
                }
                if(t>=10 && t%timeQuantum==0){
                    if(processQueue.get(currentProcessIndex).getArrivalTime() != t){
                        if (currentProcessIndex == processQueue.size() - 1) {
                            currentProcessIndex = 0;
                            //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                            writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                        } else {
                            currentProcessIndex++;
                            //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                            writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                        }
                    }else{
                        currentProcessIndex++;
                        if (currentProcessIndex == processQueue.size() - 1) {
                            currentProcessIndex = 0;
                            //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                            writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                        } else {
                            currentProcessIndex++;
                            //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                            writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(currentProcessIndex).getProcessID());
                        }
                    }
                }
                if (!processQueue.isEmpty()) {
                    try{
                        processQueue.get(currentProcessIndex).setCpuBurst(processQueue.get(currentProcessIndex).getCpuBurst() - 1);
                    }catch (Exception e){
                        //sop("Failed at: " + processQueue.get(currentProcessIndex).getCpuBurst());
                    }
                }
            }
            if(t == tMax){
                running = false;
            }
            t++;
        }
        //sop("------End of the results of Round Robin Scheduling------------------\n");
        writeOutput("------End of the results of Round Robin Scheduling------------------\n");
    }

    private static void npPriority(){
        //sop("--------Scheduling results of Priority Scheduling-------------------");
        writeOutput("--------Scheduling results of Priority Scheduling-------------------");
        LinkedList<Process> processes = copyProcesses(processInputList);
        LinkedList<Process> processQueue = new LinkedList<Process>();
        long tMax = 0;
        long t=0;
        for(Process p: processes){  //Gets total burst time of processes
            tMax+= p.getCpuBurst();
        }
        boolean running = true;
        while(running){
            for(int i=0;i<processes.size();i++){
                if(processes.get(i).getArrivalTime() == t){
                    processQueue.add(processes.get(i));
                }
                if(t==0 && !processQueue.isEmpty() && i == 0){
                    //sop("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                    writeOutput("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                }
            }//Add process when the arrival time appears
            if(!processQueue.isEmpty()){
                if(processQueue.get(0).getCpuBurst() == 0) {
                    processQueue.remove(0);
                    Collections.sort(processQueue, new Comparator<Process>() {
                        @Override
                        public int compare(Process o1, Process o2) {
                            return o1.getPriority() > o2.getPriority() ? -1 : o1.getPriority() == o2.getPriority() ? 0 : 1;
                        }
                    });
                    if(!processQueue.isEmpty()){
                        //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
                        writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
                    }
                }
            }
            if(!processQueue.isEmpty()){
                processQueue.get(0).setCpuBurst(processQueue.get(0).getCpuBurst()-1);
            }//Reduce time left by 1
            if(t == tMax){
                running = false;
            }
            t++;
        }
        //sop("------End of the results of Priority Scheduling------\n");
        writeOutput("------End of the results of Priority Scheduling------\n");
    }//End npPriority

    private static void npSJF(){
        //sop("------Scheduling results of Non Preemptive Shortest Job First-------");
        writeOutput("------Scheduling results of Non Preemptive Shortest Job First-------");
        LinkedList<Process> processes = copyProcesses(processInputList);
        LinkedList<Process> processQueue = new LinkedList<Process>();
        long tMax = 0;
        long t=0;
        for(Process p: processes){  //Gets total burst time of processes
            tMax+= p.getCpuBurst();
        }
        boolean running = true;
        while(running){
            for(int i=0;i<processes.size();i++){
                if(processes.get(i).getArrivalTime() == t){
                    processQueue.add(processes.get(i));
                }
                if(t==0 && !processQueue.isEmpty() && i == 0){
                    //sop("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                    writeOutput("At time " + t + "ms,  CPU starts running process " + processQueue.get(0).getProcessID());
                }
            }//Add process when the arrival time appears
            if(!processQueue.isEmpty()){
                if(processQueue.get(0).getCpuBurst() == 0) {
                    processQueue.remove(0);
                    Collections.sort(processQueue, new Comparator<Process>() {
                        @Override
                        public int compare(Process o1, Process o2) {
                            return o1.getCpuBurst() < o2.getCpuBurst() ? -1 : o1.getCpuBurst() == o2.getCpuBurst() ? 0 : 1;
                        }
                    });
                    if(!processQueue.isEmpty()){
                        //sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
                        writeOutput("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
                    }
                }
            }
            if(!processQueue.isEmpty()){
                processQueue.get(0).setCpuBurst(processQueue.get(0).getCpuBurst()-1);
            }//Reduce time left by 1
            if(t == tMax){
                running = false;
            }
            t++;
        }
        //sop("-------End of the results of Non Preemptive Shortest Job First------\n");
        writeOutput("-------End of the results of Non Preemptive Shortest Job First------\n");
    }//End npSJF

    private static void readInput() {
        try {
            File input = new File("src\\processes.txt");
            Scanner sc = new Scanner(input);
            do{
                String[] nextProcess = trimInput(sc.nextLine());
                Process value = new Process(convertToLong(nextProcess[0]),convertToLong(nextProcess[1]),
                                            convertToLong(nextProcess[2]),convertToLong(nextProcess[3]));
                //sop(value.printProcess());
                processInputList.add(value);
            }while(sc.hasNextLine());
        }catch(Exception e){
            sop("File input failed");
        }
    }

    private static LinkedList<Process> copyProcesses(LinkedList<Process> processList) {
        LinkedList<Process> newList = new LinkedList<Process>();
        for(Process s: processList){
            Process newProcess = new Process(s.getProcessID(),s.getArrivalTime(),s.getPriority(),s.getCpuBurst());
            newList.add(newProcess);
        }
        return newList;
    }

    private static void printProcesses(LinkedList<Process> processList){
        for(Process p: processList) {
            //sop(p.printProcess());
            writeOutput(p.printProcess());
        }

    }

    private static void header(){
        writeOutput("####################################################################################");
        writeOutput("#                                                                                  #");
        writeOutput("#                      CS3600 - HW05 - Scheduling Algorithms                       #");
        writeOutput("#                          Author: Austin Gailey                                   #");
        writeOutput("#                             Date: 4 Oct 2019                                     #");
        writeOutput("#                                                                                  #");
        writeOutput("####################################################################################\n");
    }

    private static long convertToLong(String s){
        return Long.parseLong(s);
    }

    private static String[] trimInput(String s){
        String[] inputValues;
        String newString = s.replace(">","").replace("<","")
                            .replace("P","");
        inputValues = newString.split(",");
        return inputValues;
    }

    private static void initializeOutputFile(){
        try{
            BufferedWriter output = new BufferedWriter(new FileWriter("HW05Out.txt",false));
            output.append("");
        }catch (IOException ioe){
            sop("Failed to initialize output file");
        }
    }

    private static void writeOutput(String s) {
        try{
            BufferedWriter output = new BufferedWriter(new FileWriter("HW05Out.txt",true));
            output.append(s);
            output.append("\n");
            output.close();
        }catch(IOException ioe){
            sop("Failed to write to file");
        }

    }

    private static void sop(String s){
        System.out.println(s);
    }
}