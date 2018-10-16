package br.ufal.ic.academico.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {

    public RestResponse(String reason) {
        code = 400;
        this.reason = reason;
    }


    private int code;
    private String reason;
}
