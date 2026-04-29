package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Reader;
import com.ehxy.tsg.mapper.ReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderMapper readerMapper;

    public Reader selectById(Long id) {
        return readerMapper.selectById(id);
    }

    public Reader selectByReaderNo(String readerNo) {
        return readerMapper.selectByReaderNo(readerNo);
    }

    public List<Reader> selectAll() {
        return readerMapper.selectAll();
    }

    public List<Reader> selectByCondition(Reader reader) {
        return readerMapper.selectByCondition(reader);
    }

    public int insert(Reader reader) {
        return readerMapper.insert(reader);
    }

    public int update(Reader reader) {
        return readerMapper.update(reader);
    }

    public int deleteById(Long id) {
        return readerMapper.deleteById(id);
    }

    public int countActiveReaders() {
        return readerMapper.countActiveReaders();
    }

    public Reader login(String readerNo, String password) {
        Reader reader = readerMapper.selectByReaderNo(readerNo);
        if (reader != null && reader.getPassword() != null && reader.getPassword().equals(password)) {
            return reader;
        }
        return null;
    }

    public int register(Reader reader) {
        reader.setStatus(1);
        reader.setMaxBorrowCount(5);
        return readerMapper.insert(reader);
    }

    public int updatePassword(Long id, String newPassword) {
        Reader reader = readerMapper.selectById(id);
        if (reader != null) {
            reader.setPassword(newPassword);
            return readerMapper.update(reader);
        }
        return -1;
    }
}
