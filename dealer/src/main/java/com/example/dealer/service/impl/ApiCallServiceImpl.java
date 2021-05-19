package com.example.dealer.service.impl;

import com.example.dealer.document.ApiCall;
import com.example.dealer.enums.ApiCallEnum;
import com.example.dealer.repository.mongo.ApiCallRepository;
import com.example.dealer.service.ApiCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ApiCallServiceImpl implements ApiCallService {

    @Autowired
    private ApiCallRepository apiCallRepository;

    @Override
    public void log(ApiCallEnum callEnum) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        ApiCall apiCall = new ApiCall();
        apiCall.setIp(request.getRemoteAddr());
        apiCall.setDate(new Date());
        apiCall.setService(callEnum);
        apiCallRepository.insert(apiCall);
    }
}
