package com.example.dealer.service;

import com.example.dealer.document.ApiCall;
import com.example.dealer.enums.ApiCallEnum;
import com.example.dealer.repository.mongo.ApiCallRepository;
import com.example.dealer.service.impl.ApiCallServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiCallServiceTest {

    private static final Date DATE = new Date();

    @Mock
    private ApiCallRepository repository;

    @Mock
    private ServletRequestAttributes servletRequestAttributes;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private ApiCallServiceImpl service;

    @Before
    public void init(){
        MockedStatic<RequestContextHolder> requestContextHolderMock = mockStatic(RequestContextHolder.class);
        requestContextHolderMock.when(RequestContextHolder::currentRequestAttributes).thenReturn(servletRequestAttributes);
        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
    }

    @Test
    public void logTest_OK(){
        when(repository.insert(any(ApiCall.class))).thenReturn(apiCallMock());

        service.log(ApiCallEnum.CARS_BY_FILTER);

        verify(repository, times(1)).insert(any(ApiCall.class));
    }

    private ApiCall apiCallMock(){
        ApiCall apiCall = new ApiCall();
        apiCall.setId("18974948944");
        apiCall.setDate(DATE);
        apiCall.setIp("127.0.0.1");
        apiCall.setService(ApiCallEnum.CARS_BY_FILTER);
        return apiCall;
    }
}
