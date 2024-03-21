package com.wu.transaction.entity;

public enum ReminderStatus {
    PENDING, // Email needs to be sent
    SENT, // Email has been sent
    CANCELLED;// Reminder was cancelled, no email will be sent
}
