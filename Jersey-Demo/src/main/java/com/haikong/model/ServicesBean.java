package com.haikong.model;

public class ServicesBean {
    /**
     * status : 1
     */

    private OperationStatusBean operation_status;
    /**
     * on_off : true
     */

    private PowerSwitchBean power_switch;

    public ServicesBean(int status,boolean on_off){
        operation_status = new OperationStatusBean();
        operation_status.setStatus(status);
        power_switch = new PowerSwitchBean();
        power_switch.setOn_off(on_off);
    }

    public ServicesBean(){}

    public OperationStatusBean getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(OperationStatusBean operation_status) {
        this.operation_status = operation_status;
    }

    public PowerSwitchBean getPower_switch() {
        return power_switch;
    }

    public void setPower_switch(PowerSwitchBean power_switch) {
        this.power_switch = power_switch;
    }

    public static class OperationStatusBean {
        private int status = 0;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class PowerSwitchBean {
        private boolean on_off = false;

        public boolean getOn_off() {
            return on_off;
        }

        public void setOn_off(boolean on_off) {
            this.on_off = on_off;
        }
    }
}