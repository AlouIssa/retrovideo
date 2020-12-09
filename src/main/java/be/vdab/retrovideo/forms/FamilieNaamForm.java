package be.vdab.retrovideo.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FamilieNaamForm {
    @NotBlank
    private final String familieNaamInput;
    public FamilieNaamForm(@NotBlank String familieNaamInput) {
        this.familieNaamInput = familieNaamInput;
    }
    public String getFamilieNaamInput() {
        return familieNaamInput;
    }
}
