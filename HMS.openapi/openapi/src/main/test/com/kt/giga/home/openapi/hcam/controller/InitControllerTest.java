package com.kt.giga.home.openapi.hcam.controller;

import com.kt.giga.home.openapi.common.AuthToken;
import com.kt.giga.home.openapi.hcam.domain.AppInit;
import com.kt.giga.home.openapi.hcam.service.InitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InitControllerTest {

    @Mock
    private InitService initService;

    @InjectMocks
    private InitController initController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController).build();
    }

    @Test
    public void testInit() throws Exception {

        String deviceId = "ffffffff-e07f-412a-0033-c5870033c587";
        String telNo = null;
        String pnsRegId = "0WU9N103u046000000000I0q0Z3T1o";
        String svcCode = "001";

        AppInit appInit = new AppInit();
        appInit.setDeviceId(deviceId);
        appInit.setAuthToken(AuthToken.encodeAuthToken(deviceId, telNo, pnsRegId, "", "", svcCode).getToken());
        appInit.setNewAppVer("0.0.65.0");
        appInit.setAppUpOption("2");
        appInit.setNoticeOption("0");
        appInit.setPopupNoticeOption("0");
        appInit.setEcServerList(new String[]{"59.18.50.31", "59.18.50.32", "59.18.50.33", "59.18.50.34"});
        appInit.setEcPort("9077");
        appInit.setPnsImgUrl("http://211.42.137.221:8080/pns");
        appInit.setNoticeUrl("");
        appInit.setPrivacyPolicyUrl("https://www.ohcam.olleh.com/www/webview/policy?uid\\u003d10000\\u0026cpCode\\u003d001");
        appInit.setJoinGuideUrl("m.olleh.com");
        appInit.setUcloudJoinGuideUrl("http://www.olleh.com");
        appInit.setWelcomeGuideUrlList(new String[]{
                "https://www.ohcam.olleh.com/img/upload/welcome/201504/9ba84667-1173-4e94-b29c-883408481f46.png",
                "https://www.ohcam.olleh.com/img/upload/welcome/201504/bd0c7e61-952b-4338-80dd-1789d67020ca.png",
                "https://www.ohcam.olleh.com/img/upload/welcome/201504/e599ee72-30bd-4235-b88d-622e3a25b9b7.png"
        });
        appInit.setCoachGuideUrlList(new String[]{
                "https://www.ohcam.olleh.com/img/upload/coach/201505/1601/4d4cdc92-b162-408b-abda-07a5af98e4fa.png",
                "https://www.ohcam.olleh.com/img/upload/coach/201504/1602/89ac9ea2-8cc3-48e3-ab0c-a2a658824aa8.png",
                "https://www.ohcam.olleh.com/img/upload/coach/201504/1603/ac613cc8-842d-46d4-833b-e63d600ebf9d.png"
        });
        appInit.setCamGuideUrlList(new String[]{
                "https://www.ohcam.olleh.com/img/upload/camera/201505/d06fb32f-ab4a-411f-837d-bd76666b0b05.png",
                "https://www.ohcam.olleh.com/img/upload/camera/201505/b720c761-9b66-43d5-a6da-005a68688261.png",
                "https://www.ohcam.olleh.com/img/upload/camera/201505/5aa6bc55-8194-4c53-9d25-91f7f14bf29a.png"
        });

        when(initService.init(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(appInit);

        this.mockMvc.perform(post("/user/init")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"screenSize\": \"h\",\n" +
                        "  \"pnsRegId\": \"" + pnsRegId + "\",\n" +
                        "  \"appVer\": \"0.0.98\",\n" +
                        "  \"svcCode\": \"" + svcCode + "\",\n" +
                        "  \"deviceId\": \"" + deviceId + "\"\n" +
                        "}\n")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.noticeOption", is("0")))
                .andExpect(jsonPath("$.deviceId", is("ffffffff-e07f-412a-0033-c5870033c587")));

        verify(initService, times(1)).init(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        verifyNoMoreInteractions(initService);

    }

    @Test
    public void testGetAppVersion() throws Exception {

    }

}