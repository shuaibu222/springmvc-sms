package com.shuaibu.service;

import com.shuaibu.dto.ResultCheckingDto;
import com.shuaibu.model.ReportSheetModel;

public interface ResultCheckingService {
    ReportSheetModel searchResult(ResultCheckingDto resultCheckingDto);
}
