package com.example.service;


import com.example.entity.SmsHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.SmsHistoryRepository;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${my.eskiz.uz.email}")
    private String myEskizUzEmail;

    @Value("${my.eskiz.uz.password}")
    private String myEskizUzPassword;


    // TODO           METHOD
    public String sendSms(String phone) {
//        String code = RandomUtil.getRandomSmsCode();
        String code = "Bu Eskiz dan test";
        String message = "Bu Eskiz dan test"; // todo Kontent provayder tomonidan berilgan test uchun sms
        send(phone, message);

        saveSmsHistory(phone, code, message); // todo smsni save qilamiz
        return "Kod muvaffaqiyatli yuborildi";
    }
//    String currentToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTkyMTkxMjEsImlhdCI6MTcxNjYyNzEyMSwicm9sZSI6InRlc3QiLCJzaWduIjoiYWRmODk1MDJhZDAyYjBlNDJjNTgwYTNiYmE3NmMyNGQwNjlhYWRmMTQ5NWY2N2Y1ZmEwNjc5OTBlMTE4YjU4NiIsInN1YiI6Ijc0MDIifQ.JsGEGpML-svM4ZJe0C3v4vp49zoCWcm6W_MJFrmrx4s";

    private void send(String phone, String message) {
        String token = "Bearer " + getToken();

//        if (!tokenService.checkSmsToken(currentToken)){  // todo Jasur aka
//            String newToken = getToken();
//            tokenService.saveToken(newToken);
//            currentToken = newToken;
//        }


        String prPhone = phone;
        if (prPhone.startsWith("+")) {
            prPhone = prPhone.substring(1);
        }
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", prPhone)
                .addFormDataPart("message", message)
                .addFormDataPart("from", "4546")
                .build();

        Request request = new Request.Builder()
                .url(smsUrl + "api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String getToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", myEskizUzEmail)
                .addFormDataPart("password", myEskizUzPassword)
                .build();
        Request request = new Request.Builder()
                .url(smsUrl + "api/auth/login")
                .method("POST", body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    // Sms history save method
    /*bor*/
    public void saveSmsHistory(String phone, String code, String message) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setMessage(message);
        entity.setCode(code);
        smsHistoryRepository.save(entity);
    }

    public void isNotExpiredSms(String phone) { // todo SMS codeni tasdiqlash vaqti
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(phone);
        if (optional.isEmpty()) {
            throw new AppBadException(phone + " Topilmadi");
        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusMinutes(3).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Tasdiqlash muddati tugadi");
        }
    }

    public void checkSmsLimit(String phone) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(1);
        long count = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, to, from);

        if (count >= 3) {
            throw new AppBadException("SMS limitiga yetdi. Iltimos, biroz vaqtdan keyin harakat qilib ko'ring");
        }
    }

}
