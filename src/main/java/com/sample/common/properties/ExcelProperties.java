package com.sample.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "excel")
@RequiredArgsConstructor
@Getter
public class ExcelProperties {
    @NotBlank
    private final String fileDirPath;
    @NotBlank
    private final String tmpDirPath;
}
