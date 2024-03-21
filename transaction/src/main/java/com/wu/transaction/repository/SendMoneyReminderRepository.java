package com.wu.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wu.transaction.entity.SendMoneyReminder;


public interface SendMoneyReminderRepository extends JpaRepository<SendMoneyReminder,Long>{
   
}
