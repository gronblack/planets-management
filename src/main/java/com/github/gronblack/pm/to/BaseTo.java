package com.github.gronblack.pm.to;

import com.github.gronblack.pm.model.HasId;
import com.github.gronblack.pm.util.validation.NoHtml;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseTo implements HasId {
    protected Integer id;

    @NotBlank
    @Size(min = 1, max = 100)
    @NoHtml
    protected String name;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id + '[' + name + ']';
    }
}
