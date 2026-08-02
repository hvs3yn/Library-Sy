package com.huseyn;

import java.util.List;
import java.util.Optional;

public interface DBDAO {

    Optional<Book> findById(String id);
}
