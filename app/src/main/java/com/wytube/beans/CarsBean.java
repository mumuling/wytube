package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class CarsBean {


    /**
     * data : [{"car":{"carId":"163669FE4B4C4CFDAA671D6284FFCB92","name":"粤c11111","num":"拓海"},"park":{"ip":"192.168.0.5","name":"停车场#A","parkId":"F32F73CA6AB14C228C83A97F68C51D66","port":"80"}},{"car":{"carId":"72A5A45DB8E241878E0F2D7BE30A45D4","name":"粤c11111","num":"拓海1"},"park":{"ip":"192.168.0.5","name":"停车场#C","parkId":"4C0A7642EC074BC0B62BE3D8DA287068","port":"80"}}]
     * date : 2017-06-13 15:17:20
     * message : OK
     * state : SUCCESS
     * success : true
     */

    private String date;
    private String message;
    private String state;
    private boolean success;
    private List<DataBean> data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * car : {"carId":"163669FE4B4C4CFDAA671D6284FFCB92","name":"粤c11111","num":"拓海"}
         * park : {"ip":"192.168.0.5","name":"停车场#A","parkId":"F32F73CA6AB14C228C83A97F68C51D66","port":"80"}
         */

        private CarBean car;
        private ParkBean park;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public ParkBean getPark() {
            return park;
        }

        public void setPark(ParkBean park) {
            this.park = park;
        }

        public static class CarBean {
            /**
             * carId : 163669FE4B4C4CFDAA671D6284FFCB92
             * name : 粤c11111
             * num : 拓海
             */

            private String carId;
            private String name;
            private String num;

            public String getCarId() {
                return carId;
            }

            public void setCarId(String carId) {
                this.carId = carId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class ParkBean {
            /**
             * ip : 192.168.0.5
             * name : 停车场#A
             * parkId : F32F73CA6AB14C228C83A97F68C51D66
             * port : 80
             */

            private String ip;
            private String name;
            private String parkId;
            private String port;

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }
        }
    }
}
