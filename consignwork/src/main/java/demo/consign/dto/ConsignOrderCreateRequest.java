package demo.consign.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsignOrderCreateRequest {

    @NotNull
    private Long shipperId;

    @NotBlank
    private String startAddress;

    @NotBlank
    private String endAddress;

    @NotBlank
    private String goodsDescription;

    @NotNull
    @Min(1)
    private BigDecimal totalPrice;
}
