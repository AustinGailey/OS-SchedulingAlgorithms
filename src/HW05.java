import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.LinkedList;

public class HW05 {
    //Declaring Global Variables
    //private static File input;
    private static File output;
    //private static Scanner sc;
    private static LinkedList<Process> processInputList = new LinkedList<Process>();


    public static void main(String[] args) {
        // write your code here
        readInput();
        printProcesses(processInputList);
        npSJF(); //Run non-preemptive SJF
        npPriority();
    }

    private static void npPriority(){
        sop("---Scheduling results of Non Preemptive Shortest Job First");
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
                    sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
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
                        sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
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
        sop("---End of the results of Non Preemptive Shortest Job First\n");
    }//End npPriority

    private static void npSJF(){
        sop("---Scheduling results of Non Preemptive Shortest Job First");
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
                    sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
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
                        sop("At time " + t + "ms, CPU starts running process " + processQueue.get(0).getProcessID());
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
        sop("---End of the results of Non Preemptive Shortest Job First\n");
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
            newList.add(s);
        }
        return newList;
    }

    private static void printProcesses(LinkedList<Process> processList){
        for(Process p: processList) {
            sop(p.printProcess());
        }

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

    private static void sop(String s){
        System.out.println(s);
    }
}