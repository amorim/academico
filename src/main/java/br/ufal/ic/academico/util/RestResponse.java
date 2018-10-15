package br.ufal.ic.academico.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestResponse {

    public RestResponse(String reason) {
        code = 400;
        this.reason = reason;
    }


    private int code;
    private String reason;
}
