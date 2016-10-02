package com.DAO.Interface;

import com.Bean.File;

import java.util.Set;

public interface FileDao extends Dao<File> {

    Set<File> getAll(Long user_id);
}
