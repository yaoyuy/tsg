package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.FineRecord;
import com.ehxy.tsg.mapper.FineRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FineRecordService {
    @Autowired
    private FineRecordMapper fineRecordMapper;

    public FineRecord selectById(Long id) {
        return fineRecordMapper.selectById(id);
    }

    public List<FineRecord> selectByReaderId(Long readerId) {
        return fineRecordMapper.selectByReaderId(readerId);
    }

    public List<FineRecord> selectUnpaid() {
        return fineRecordMapper.selectUnpaid();
    }

    public int insert(FineRecord fineRecord) {
        return fineRecordMapper.insert(fineRecord);
    }

    public int markAsPaid(Long id) {
        return fineRecordMapper.markAsPaid(id);
    }

    public BigDecimal sumUnpaidByReaderId(Long readerId) {
        return fineRecordMapper.sumUnpaidByReaderId(readerId);
    }

    public int countUnpaid() {
        return fineRecordMapper.countUnpaid();
    }

    public void calculateFine(Long borrowId, Long readerId) {
        FineRecord fineRecord = new FineRecord();
        fineRecord.setBorrowId(borrowId);
        fineRecord.setReaderId(readerId);
        fineRecord.setAmount(new BigDecimal("1.00"));
        fineRecord.setIsPaid(0);
        fineRecordMapper.insert(fineRecord);
    }
}
