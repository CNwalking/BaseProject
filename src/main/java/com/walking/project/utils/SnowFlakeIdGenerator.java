package com.walking.project.utils;


/**
 * @Author: CNwalking
 * @DateTime: 2020/4/14 16:08
 * @Description:雪花算法生成唯一id
 */
public class SnowFlakeIdGenerator {
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    static final SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(1L, 1L);

    public SnowFlakeIdGenerator(long workerId, long datacenterId) {
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 31L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("DataCenterID 不能大于 %d 或小于 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("WorkerID 不能大于 %d 或小于 0", 31L));
        }
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException("当前时间小于上一次记录的时间戳！");
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1483200000000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = System.currentTimeMillis(); timestamp <= lastTimestamp; timestamp = System.currentTimeMillis()) {
        }

        return timestamp;
    }

    public static long getIdGenerator() {
        return idGenerator.nextId();
    }

//    public static void main(String[] args) {
//        String meetingId = String.valueOf(getIdGenerator());
//        System.out.println(meetingId);
//    }

}
