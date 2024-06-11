package cosmetics.BOGOShop.utils;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest; //암호화 해시 함수 구현
import java.security.NoSuchAlgorithmException; //예외 클래스 (지정된 암호화 알고리즘 사용할 수 없을때)

@Log4j2
public class SHA256Util {
    public static final String ENCRYPTION_TYPE = "SHA-256"; //해시 알고리즘 지정
    //입력된 문자열을 해시 알고리즘을 사용하여 암호화하고 결과 해시값을 반환
    public static String encryptSHA256(String str) {
        String SHA = null; //암호화된 문자열
        MessageDigest sh;

        try {
            sh = MessageDigest.getInstance(ENCRYPTION_TYPE); //SHA-256 알고리즘 초기화
            sh.update(str.getBytes()); //입력 문자열을 바이트 배열로 변환
            byte[] byteData = sh.digest(); //해시 계산을 수행하고, 결과를 바이트배열로 반환
            StringBuilder sb = new StringBuilder();//해사값을 문자열로 변환
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString(); //최종 생성된 문자열을 변수 저장
            log.info("원본 비밀번호: " + str);
            log.info("암호화 비밀번호: " + SHA);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 에러!", e);
        }
        return SHA;
    }
}
