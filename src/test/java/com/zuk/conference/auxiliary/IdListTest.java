package com.zuk.conference.auxiliary;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IdListTest {

    @Test
    void parsesIdsAndSkipsGarbage() {
        assertThat(IdList.parse("60,61,")).containsExactly(60, 61);
        assertThat(IdList.parse("null16,4,")).containsExactly(4);
        assertThat(IdList.parse(null)).isEmpty();
        assertThat(IdList.parse("")).isEmpty();
    }

    @Test
    void formatsWithTrailingComma() {
        assertThat(IdList.format(List.of(1, 2))).isEqualTo("1,2,");
        assertThat(IdList.format(List.of())).isEmpty();
    }

    @Test
    void addDoesNotDuplicate() {
        assertThat(IdList.add(null, 5)).isEqualTo("5,");
        assertThat(IdList.add("5,", 5)).isEqualTo("5,");
        assertThat(IdList.add("5,", 7)).isEqualTo("5,7,");
    }

    @Test
    void removeAndContains() {
        assertThat(IdList.remove("1,2,3,", 2)).isEqualTo("1,3,");
        assertThat(IdList.remove("1,", 1)).isEmpty();
        assertThat(IdList.contains("1,2,", 2)).isTrue();
        assertThat(IdList.contains("12,", 2)).isFalse();
    }
}
