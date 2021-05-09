package com.jgranados.workplanner.shifts.enums;

public enum ShiftHour {
    SHIFT_0_8(0, 8),
    SHIFT_8_16(8, 16),
    SHIFT_16_24(16, 0);

    private int startHour;
    private int endHour;

    ShiftHour(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
