package com.roombooking.model;

import java.util.List;

public class TimeBound implements  Cloneable{
        public String startTime;
        public String endTime;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeBound timeBound = (TimeBound) o;

            if (startTime != null ? !startTime.equals(timeBound.startTime) : timeBound.startTime != null)
                return false;
            return endTime != null ? endTime.equals(timeBound.endTime) : timeBound.endTime == null;

        }

        @Override
        public int hashCode() {
            int result = startTime != null ? startTime.hashCode() : 0;
            result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
            return result;
        }

    @Override
    public TimeBound clone() {
        TimeBound clone = null;
        try{
            clone = (TimeBound) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }

        return clone;

    }

    public static List<TimeBound> createDeepcopy(List<TimeBound> timeBoundList,List<TimeBound> timeList){

        for (TimeBound times: timeBoundList){
            timeList.add(times.clone());
        }
        return timeList;
    }

    }