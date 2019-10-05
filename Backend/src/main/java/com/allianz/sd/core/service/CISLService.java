package com.allianz.sd.core.service;

import org.springframework.stereotype.Service;

import com.allianz.sd.core.service.bean.CISLToken;

@Service
public class CISLService {

    public CISLToken getToken() {
        //呼叫WebService取得Token
        return new CISLToken();
    }
    
    public static void main (String args[]) {
    	CISLService a = new CISLService ();
    	String abc = a.filterSymbol("TWD 1234.392");
    	System.out.println(abc);
    }
    
    public String filterSymbol(String symbolString) {
		StringBuffer stringBuffer = new StringBuffer();
			for(int i = 0 ; i < symbolString.length() ; i++) {
				char c = symbolString.charAt(i);
				if(!Character.isLetter(c)) {
					stringBuffer.append(c);
				}
			}
		return stringBuffer.toString().replace(" ", "");
	}

}
