package ru.serdyuk.diff.entities;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DiffOffset {

    int position;

    int length;
}
