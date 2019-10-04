public class Process {

    private long processID;
    private long arrivalTime;
    private long priority;
    private long cpuBurst;


    public Process(long processID, long arrivalTime, long priority, long cpuBurst){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.cpuBurst = cpuBurst;
    }

    public long getProcessID() {
        return this.processID;
    }

    public void setProcessID(long processID) {
        this.processID = processID;
    }

    public long getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getPriority() {
        return this.priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public long getCpuBurst() {
        return this.cpuBurst;
    }

    public void setCpuBurst(long cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public String printProcess() {
        return  "<P" + this.processID + ">," + "<" + this.arrivalTime + ">," +
                "<" + this.priority + ">," + "<" + this.cpuBurst + ">";
    }
}
