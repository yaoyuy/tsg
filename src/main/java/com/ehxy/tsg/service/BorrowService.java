package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.BorrowRecord;
import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderMapper readerMapper;

    public BorrowRecord selectById(Long id) {
        return borrowRecordMapper.selectById(id);
    }

    public List<BorrowRecord> selectByReaderId(Long readerId) {
        return borrowRecordMapper.selectByReaderId(readerId);
    }

    public List<BorrowRecord> selectActiveBorrows() {
        return borrowRecordMapper.selectActiveBorrows();
    }

    public List<BorrowRecord> selectByCondition(BorrowRecord record) {
        return borrowRecordMapper.selectByCondition(record);
    }

    public List<BorrowRecord> selectAll() {
        return borrowRecordMapper.selectAll();
    }

    @Transactional
    public int borrowBook(Long readerId, Long bookId) {
        Reader reader = readerMapper.selectById(readerId);
        Book book = bookMapper.selectById(bookId);
        
        if (reader == null || book == null) {
            return -1;
        }
        
        int activeBorrows = borrowRecordMapper.countActiveBorrowsByReader(readerId);
        if (activeBorrows >= reader.getMaxBorrowCount()) {
            return -2;
        }
        
        if (book.getAvailableCount() <= 0) {
            return -3;
        }
        
        BorrowRecord record = new BorrowRecord();
        record.setReaderId(readerId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(30));
        record.setStatus(0);
        
        bookMapper.decreaseAvailableCount(bookId);
        return borrowRecordMapper.insert(record);
    }

    @Transactional
    public int returnBook(Long borrowId) {
        BorrowRecord record = borrowRecordMapper.selectById(borrowId);
        if (record == null) {
            return -1;
        }
        
        record.setReturnDate(LocalDate.now());
        record.setStatus(1);
        
        if (record.getReturnDate().isAfter(record.getDueDate())) {
            long days = record.getReturnDate().toEpochDay() - record.getDueDate().toEpochDay();
            record.setFine(BigDecimal.valueOf(days * 0.5));
        } else {
            record.setFine(BigDecimal.ZERO);
        }
        
        bookMapper.increaseAvailableCount(record.getBookId());
        return borrowRecordMapper.update(record);
    }

    public int countAll() {
        return borrowRecordMapper.countAll();
    }

    @Transactional
    public int renewBook(Long borrowId) {
        BorrowRecord record = borrowRecordMapper.selectById(borrowId);
        if (record == null || record.getStatus() != 0) {
            return -1;
        }
        record.setDueDate(record.getDueDate().plusDays(30));
        return borrowRecordMapper.update(record);
    }

    public List<BorrowRecord> selectOverdue() {
        return borrowRecordMapper.selectOverdue();
    }

    public int countOverdue() {
        return borrowRecordMapper.countOverdue();
    }
}
